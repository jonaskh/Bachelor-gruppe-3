import React, { useState, useEffect } from 'react';
import { Table, Pagination } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import apiServiceInstance from "./APIService";
import ShipmentEvalChart from "./components/ShipmentEvalChart";
import ShipmentEvalBar from "./components/ShipmentEvalBar";

function App() {



    return (
        <>

            <h2>Shipment</h2>
            <ShipmentEvalChart />

        </>
    );

}

export default App;