import 'bootstrap/dist/css/bootstrap.min.css';
import ShipmentEvalChart from "./components/ShipmentEvalChart";
import CheckpointEvalChart from "./components/CheckpointEvalChart";
import CustomerTable from "./components/CustomerTable";
import TerminalTable from "./components/TerminalTable";


function App() {



    return (
        <>

            <div style={{ textAlign: "center", margin: "20px", padding: "20px" }}>
                <h1>Assessing data access layers for:</h1>
                <img src={process.env.PUBLIC_URL + '/postnord.jpg'} alt="Postnord logo" />
            </div>

            <div style={{margin: "20px", padding: "10px" }}>
                <h2>Table of customers</h2>
                <CustomerTable />
            </div>

            <div style={{margin: "20px", padding: "10px" }}>
                <h2>Table of terminals</h2>
                <TerminalTable />
            </div>


            <div style={{margin: "20px", padding: "10px" }}>
                <h2>Shipment Graph</h2>
                <ShipmentEvalChart />

                <h2>Checkpoint Graph</h2>
                <CheckpointEvalChart />
            </div>

        </>
    );

}

export default App;