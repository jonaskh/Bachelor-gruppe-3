import React, { useState, useEffect } from 'react';
import Table from 'react-bootstrap/Table';
import 'bootstrap/dist/css/bootstrap.min.css';
import apiServiceInstance from "./APIService";

function App() {
    const [data, setData] = useState([]);


    useEffect(() => {
        apiServiceInstance.getCustomers()
            .then(response => setData(response.data))
            .catch(error => console.log(error));
    }, []);

    /*const handleAddData = () => {
        apiServiceInstance.addCustomers(newData)
            .then(response => {
                const newData = response.data;
                setData([...data, newData]);
            })
            .catch(error => console.log(error));
    };*/

    const columns = [
        { dataField: 'customerID', text: 'ID' },
        { dataField: 'address', text: 'Address'},
        { dataField: 'name', text: 'Name' },
        { dataField: 'zip', text: 'Zip Code' },
    ];

    return (
        <>
            {/*<button onClick={handleAddData}>Add Data</button>*/}
            <Table striped bordered hover>
                <thead>
                <tr>
                    {columns.map((column) => (
                        <th key={column.dataField}>{column.text}</th>
                    ))}
                </tr>
                </thead>
                {/*columns[0] to fix children not having unique key*/}
                <tbody>
                {data.map((row) => (
                    <tr key={columns[0]}>
                        {columns.map((column) => (
                            <td key={column.dataField}>{row[column.dataField]}</td>
                        ))}
                    </tr>
                ))}
                </tbody>
            </Table>
        </>
    );
}

export default App;