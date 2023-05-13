import React, { useState, useEffect } from 'react';
import { Table, Pagination } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import apiServiceInstance from "../APIService";


function TerminalTable() {

    const [terminalData, setTerminalData] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);


    useEffect(() => {
        apiServiceInstance.getTerminals()
            .then(response => {
                setTerminalData(response.data);
                console.log(response.data);
            })
            .catch(error => console.log(error));
    }, []);


    const terminalColumns = [
        {dataField: 'terminal_id', text: 'Terminal Id'},
        {dataField: 'address', text: 'Address'}
    ];


    return(
        <>
            <div>
                <Table striped bordered hover size="sm">
                    <thead>
                    <tr>
                        {terminalColumns.map((column) => (
                            <th key={column.dataField}>{column.text}</th>
                        ))}
                    </tr>
                    </thead>
                    <tbody>
                    {terminalData.slice((currentPage - 1) * 3, currentPage * 3).map((row) => (
                        <tr key={row.ID}>
                            {terminalColumns.map((column) => (
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
                    {Array.from({ length: Math.ceil(terminalData.length / 3) }).map((_, index) => (
                        <Pagination.Item
                            key={index}
                            active={currentPage === index + 1}
                            onClick={() => setCurrentPage(index + 1)}
                        >
                            {index + 1}
                        </Pagination.Item>
                    ))}
                    <Pagination.Next
                        disabled={currentPage === Math.ceil(terminalData.length / 3)}
                        onClick={() => setCurrentPage(currentPage + 1)}
                    />
                </Pagination>


            </div>
        </>)



}

export default TerminalTable;