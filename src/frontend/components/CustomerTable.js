import React, { useState, useEffect } from 'react';
import { Table, Pagination } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import apiServiceInstance from "../APIService";

function CustomerTable() {

    const [customerData, setCustomerData] = useState([]);

    useEffect(() => {
        apiServiceInstance.getCustomers()
            .then(response => {
                setCustomerData(response.data);
                console.log(response.data);
            })
            .catch(error => console.log(error));
    }, []);


    const [currentPage, setCurrentPage] = useState(1);

    // const handleCellClick = (cellData) => {
    //     console.log(cellData); // Or perform other actions with the cell data
    //     apiServiceInstance.getCustomerParcels(cellData)
    //         .then(response => {
    //             setTerminalData(response.data);
    //             console.log(cellData); // Or perform other actions with the cell data
    //         })
    //         .catch(error => console.log(error));
    // };



    const customerColumns = [
        { dataField: 'id', text: 'ID' },
        { dataField: 'address', text: 'Address'},
        { dataField: 'name', text: 'Name' },
        { dataField: 'zipCode', text: 'Zip Code' },
    ];


    return (
        <>
            <Table striped bordered hover size="sm">
                <thead>
                <tr>
                    {customerColumns.map((column) => (
                        <th key={column.dataField}>{column.text}</th>
                    ))}
                </tr>
                </thead>
                <tbody>
                {customerData.slice((currentPage - 1) * 20, currentPage * 20).map((row) => (
                    <tr key={row.ID}>
                        {customerColumns.map((column) => (
                            <td key={column.dataField}>
                                {row[column.dataField]}
                            </td>
                        ))}
                    </tr>
                ))}

                </tbody>
            </Table>
            <Pagination>
                <Pagination.Prev
                    disabled={currentPage === 1}
                    onClick={() => setCurrentPage(currentPage - 1)}
                />
                {Array.from({ length: Math.ceil(customerData.length / 20) }).map((_, index) => (
                    <Pagination.Item
                        key={index}
                        active={currentPage === index + 1}
                        onClick={() => setCurrentPage(index + 1)}
                    >
                        {index + 1}
                    </Pagination.Item>
                ))}
                <Pagination.Next
                    disabled={currentPage === Math.ceil(customerData.length / 20)}
                    onClick={() => setCurrentPage(currentPage + 1)}
                />
            </Pagination>

        </>
    );

}

export default CustomerTable;
