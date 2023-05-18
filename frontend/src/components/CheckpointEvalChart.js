import React, { useState, useEffect } from "react";
import apiServiceInstance from "../APIService";
import { Chart, registerables } from "chart.js";
import ChartDataLabels from "chartjs-plugin-datalabels";
import zoomOptions from "./ZoomOptions";

Chart.register(...registerables);

function CheckpointEvalChart() {
    const [evalList3, setEvalList3] = useState([]);

    useEffect(() => {
        // Fetch data from API endpoint
        apiServiceInstance.getCheckpointEval().then((response) => {
            console.log(response.data)
            const filteredData3 = response.data
                .filter((line) => line.includes("create"))
                .map((line) => {
                    const [time, checkpoint, operation] = line.split(', ');
                    return { time, checkpoint, operation };
                })
                .filter((obj) => parseInt(obj.time) !== 0 && !isNaN(parseInt(obj.time)));
            const slicedData3 = filteredData3.slice(10);

            setEvalList3(slicedData3);
        });

    }, []);

    function formatData(evalList3) {
        const data = {
            labels: evalList3.map((evaluation) => evaluation.checkpoint),
            datasets: [
                {
                    label: 'Create',
                    data: evalList3.map((evaluation) => evaluation.time),
                    fill: false,
                    borderColor: 'rgb(75, 192, 192)',
                    lineTension: 0.1,
                }
            ],
        };
        return data;
    }

    useEffect(() => {
        if (evalList3.length > 0) {
            const data = formatData(evalList3);
            const ctx = document.getElementById('checkpoint-eval-chart').getContext('2d');
            if (evalList3 < 60000) {
                new Chart(ctx, {
                    type: 'line',
                    data: data,
                    options: {
                        plugins: {
                            title: {
                                display: true,
                                text: evalList3.length + ' Valid Create operations'
                            },
                            zoom: zoomOptions,
                        },
                        responsive: true,
                        scales: {
                            y: {
                                beginAtZero: true,
                            },
                        },
                    },
                });
            }

            const barData = formatBarData(evalList3);
            const barCtx = document.getElementById('checkpoint-eval-bar-chart').getContext('2d');
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
                plugins: [ChartDataLabels]
            });
        }
    }, [evalList3]);

    function formatBarData(evalList3) {
        const timeValues = evalList3
            .filter((evaluation) => parseInt(evaluation.time) !== 0 && !isNaN(parseInt(evaluation.time)) )
            .map((evaluation) => {
                const value = parseFloat(evaluation.time);
                return value;
            });

        const data = {
            labels: ['Minimum', 'Maximum', 'Average', 'Standard Deviation'],
            datasets: [
                {
                    label: 'Create',
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
        return Math.round(sum / values.length);
    }

    function calculateStandardDeviation(values) {
        const avg = calculateAverage(values);
        const squareDiffs = values.map((val) => Math.pow(val - avg, 2));
        const avgSquareDiff = calculateAverage(squareDiffs);
        const stdDev = Math.sqrt(avgSquareDiff);
        return Math.round(stdDev);
    }


    return (
        <div style={{ display: "flex", position: "relative", height: "80vh", width: "49vw" }}>
            <canvas id="checkpoint-eval-chart"></canvas>
            <canvas id="checkpoint-eval-bar-chart"></canvas>
        </div>
    );

}

export default CheckpointEvalChart;


