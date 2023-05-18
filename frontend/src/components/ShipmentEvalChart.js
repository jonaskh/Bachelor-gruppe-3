import React, { useState, useEffect } from 'react';
import apiServiceInstance from "../APIService";
import { Chart, registerables } from 'chart.js';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import zoomPlugin from 'chartjs-plugin-zoom';
import zoomOptions from "./ZoomOptions";
import {unmountComponentAtNode} from "react-dom";


Chart.register(...registerables);
Chart.register(zoomPlugin);

function ShipmentEvalChart() {
    const [evalList, setEvalList] = useState([]);
    const [evalList2, setEvalList2] = useState([]);
    const [evalList3, setEvalList3] = useState([]);



    useEffect(() => {
        // Fetch data from API endpoint
        apiServiceInstance.getShipmentEval().then((response) => {
            //console.log(response.data)
            const filteredData = response.data
                .filter((line) => line.includes("create"))
                .map((line) => {
                    const [time, shipment, operation] = line.split(', ');
                    return {time, shipment, operation};
                })
                .filter((obj) => parseInt(obj.time) !== 0 && !isNaN(parseInt(obj.time)))
            const slicedData = filteredData.slice(10);

            setEvalList(slicedData);
            const filteredData2 = response.data
                .filter((line) => line.includes("read"))
                .map((line) => {
                    const [time, shipment, operation] = line.split(', ');
                    return {shipment, operation, time };
                })
                .filter((obj) => parseInt(obj.time) !== 0 && !isNaN(parseInt(obj.time)))
            const slicedData2 = filteredData2.slice(10);

            setEvalList2(slicedData2);

            const filteredData3 = response.data
                .filter((line) => line.includes("update"))
                .map((line) => {
                    const [time, shipment, operation] = line.split(', ');
                    return {shipment, operation, time };
                })
                .filter((obj) => parseInt(obj.time) !== 0 && !isNaN(parseInt(obj.time)))
            const slicedData3 = filteredData3.slice(10);

            setEvalList3(slicedData3);
        });

    }, []);

    function formatData(evalList, evalList3) {
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
                {
                    label: 'Update',
                    data: evalList3.map((evaluation) => evaluation.time),
                    fill: false,
                    borderColor: 'rgb(192,75,192)',
                    lineTension: 0.1,
                }
            ],
        };
        return data;
    }

    function formatData2(evalList2) {
        const data = {
            labels: evalList2.map((evaluation) => evaluation.shipment),
            datasets: [
                {
                    label: 'Read',
                    data: evalList2.map((evaluation) => evaluation.time),
                    fill: false,
                    borderColor: 'rgb(192,75,79)',
                    lineTension: 0.1,
                },
            ],
        };
        return data;
    }

    useEffect(() => {
        if (evalList.length > 0 && evalList2.length > 0 && evalList3.length > 0) {
            const data = formatData(evalList, evalList3);
            const ctx = document.getElementById('shipment-create&updateEval-chart').getContext('2d');
            new Chart(ctx, {
                type: 'line',
                data: data,
                options: {
                    plugins: {
                        title: {
                            display: true,
                            text:'CU operations for ' + evalList.length + ' valid Shipments'
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

            const data2 = formatData2(evalList2);
            const ctx2 = document.getElementById('shipment-readEval-chart').getContext('2d');
            new Chart(ctx2, {
                type: 'line',
                data: data2,
                options: {
                    plugins: {
                        title: {
                            display: true,
                            text: evalList2.length + ' Valid Read operations'
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

            const barData = formatBarData(evalList, evalList2, evalList3);
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
                plugins: [ChartDataLabels]
            });
        }
    }, [evalList, evalList2, evalList3]);

    function formatBarData(evalList, evalList2, evalList3) {
        const timeValues = evalList
            .filter((evaluation) => parseInt(evaluation.time) !== 0 && !isNaN(parseInt(evaluation.time)) )
            .map((evaluation) => {
                const value = parseFloat(evaluation.time);
                return value;
            });

        const timeValues2 = evalList2
            .filter((evaluation) => parseInt(evaluation.time) !== 0 && !isNaN(parseInt(evaluation.time)) )
            .map((evaluation) => {
                const value = parseFloat(evaluation.time);
                return value;
            });

        const timeValues3 = evalList3
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
                {
                    label: 'Read',
                    data: [
                        Math.min(...timeValues2),
                        Math.max(...timeValues2),
                        calculateAverage(timeValues2),
                        calculateStandardDeviation(timeValues2),
                    ],
                    backgroundColor: ['rgba(192,75,79,0.2)'],
                    borderColor: ['rgb(192,75,79)'],
                    borderWidth: 1,
                },
                {
                    label: 'Update',
                    data: [
                        Math.min(...timeValues3),
                        Math.max(...timeValues3),
                        calculateAverage(timeValues3),
                        calculateStandardDeviation(timeValues3),
                    ],
                    backgroundColor: ['rgba(192,75,192,0.2)'],
                    borderColor: ['rgb(192,75,192)'],
                    borderWidth: 1,
                }
            ],
        };
        return data;
    }


    function calculateAverage(values) {
        const sum = values.reduce((acc, val) => acc + val, 0);
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
        <div>
            <div style={{ display: "flex", position: "relative", height: "60vh", width: "49vw" }}>
                <canvas id="shipment-create&updateEval-chart"></canvas>
                <canvas id="shipment-readEval-chart"></canvas>
            </div>
            <div style={{ display: "flex", justifyContent: "center", position: "relative", height: "60vh", width: "50vw" }}>
                <canvas id="shipment-eval-bar-chart"></canvas>
            </div>
        </div>
    );
}

export default ShipmentEvalChart;
