import React, { useState, useEffect } from "react";
import apiServiceInstance from "../APIService";
import { Chart, registerables } from "chart.js";

Chart.register(...registerables);

function ShipmentEvalBar() {
    const [evalList, setEvalList] = useState([]);

    useEffect(() => {
        // Fetch data from API endpoint
        apiServiceInstance.getShipmentEval().then((response) => {
            console.log(response.data);
            setEvalList(
                response.data.map((line) => {
                    const [time, shipment, operation] = line.split(", ");
                    return { time, shipment, operation };
                })
            );
        });
    }, []);

    function filterCreateOperation(evalList) {
        return evalList.filter((evaluation) => evaluation.operation === "create");
    }

    function getMinMaxTime(evalList) {
        const createOperations = filterCreateOperation(evalList);
        const times = createOperations.map((evaluation) => evaluation.time);
        const minTime = Math.min(...times);
        const maxTime = Math.max(...times);
        return { minTime, maxTime };
    }

    useEffect(() => {
        if (evalList.length > 0) {
            const data = formatData(evalList);
            const ctx = document.getElementById('shipment-eval-chart').getContext('2d');

            new Chart(ctx, {
                type: 'bar',
                data: data,
                options: {
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: 'Time (minutes)'
                            }
                        },
                        x: {
                            title: {
                                display: true,
                                text: 'Shipment'
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            display: true,
                            labels: {
                                font: {
                                    size: 14
                                }
                            }
                        },
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    var label = context.dataset.label || '';
                                    if (label) {
                                        label += ': ';
                                    }
                                    if (context.parsed.y !== null) {
                                        label += context.parsed.y + ' minutes';
                                    }
                                    return label;
                                }
                            }
                        }
                    }
                }
            });
        }
    }, [evalList]);

    function formatData(evalList) {
        const data = {
            labels: ['Minimum', 'Maximum', 'Average', 'Standard Deviation'],
            datasets: [
                {
                    label: 'Time',
                    data: [
                        Math.min(...evalList.map((evaluation) => evaluation.time)),
                        Math.max(...evalList.map((evaluation) => evaluation.time)),
                        evalList.reduce((acc, curr) => acc + parseFloat(curr.time), 0) / evalList.length,
                        Math.sqrt(
                            evalList
                                .map((evaluation) => parseFloat(evaluation.time))
                                .reduce((acc, curr) => acc + Math.pow(curr - (evalList.reduce((acc, curr) => acc + parseFloat(curr.time), 0) / evalList.length), 2), 0) / evalList.length
                        ),
                    ],
                    backgroundColor: 'rgb(75, 192, 192)',
                    borderColor: 'rgb(75, 192, 192)',
                    borderWidth: 1,
                },
            ],
        };
        return data;
    }

    return <canvas id="shipment-eval-bar"></canvas>;


}

export default ShipmentEvalBar;



