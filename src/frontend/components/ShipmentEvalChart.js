import React, { useState, useEffect } from 'react';
import apiServiceInstance from "../APIService";
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

function ShipmentEvalChart() {
    const [evalList, setEvalList] = useState([]);

    useEffect(() => {
        // Fetch data from API endpoint
        apiServiceInstance.getShipmentEval().then((response) => {
            console.log(response.data)
            setEvalList(
                response.data
                    .filter((line) => line.includes("shipment"))
                    .map((line) => {
                    const [time, shipment, operation] = line.split(', ');
                    return { time, shipment, operation };
                })
            );
        });
    }, []);

    function formatData(evalList) {
        const data = {
            labels: evalList.map((evaluation) => evaluation.shipment),
            datasets: [
                {
                    label: 'Create',
                    data: evalList.map((evaluation) => evaluation.time),
                    fill: false,
                    borderColor: 'rgb(75, 192, 192)',
                    lineTension: 0.1,
                },
            ],
        };
        return data;
    }

    useEffect(() => {
        if (evalList.length > 0) {
            const data = formatData(evalList);
            const ctx = document.getElementById('shipment-eval-chart').getContext('2d');
            new Chart(ctx, {
                type: 'line',
                data: data,
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true,
                        },
                    },
                },
            });

            const barData = formatBarData(evalList);
            const barCtx = document.getElementById('shipment-eval-bar-chart').getContext('2d');
            new Chart(barCtx, {
                type: 'bar',
                data: barData,
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true,
                        },
                    },
                },
            });
        }
    }, [evalList]);

    function formatBarData(evalList) {
        const timeValues = evalList
            .filter((evaluation) => parseInt(evaluation.time) !== 0 && !isNaN(parseInt(evaluation.time)) )
            .map((evaluation) => {
            const value = parseFloat(evaluation.time);
            return value;
        });
        const data = {
            labels: ['Minimum', 'Maximum', 'Average', 'Standard Deviation'],
            datasets: [
                {
                    label: 'Time',
                    data: [
                        Math.min(...timeValues),
                        Math.max(...timeValues),
                        calculateAverage(timeValues),
                        calculateStandardDeviation(timeValues),
                    ],
                    backgroundColor: ['rgba(75, 192, 192, 0.2)'],
                    borderColor: ['rgba(75, 192, 192, 1)'],
                    borderWidth: 1,
                },
            ],
        };
        return data;
    }

    function calculateAverage(values) {
        const sum = values.reduce((acc, val) => acc + val, 0);
        console.log(values);
        return sum / values.length;
    }

    function calculateStandardDeviation(values) {
        const avg = calculateAverage(values);
        const squareDiffs = values.map((val) => Math.pow(val - avg, 2));
        const avgSquareDiff = calculateAverage(squareDiffs);
        const stdDev = Math.sqrt(avgSquareDiff);
        return stdDev;
    }

    return (
        <div>
            <canvas id="shipment-eval-chart"></canvas>
            <canvas id="shipment-eval-bar-chart"></canvas>
        </div>
    );
}

export default ShipmentEvalChart;
