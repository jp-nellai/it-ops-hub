import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import Events from './pages/Events';
import Alarms from './pages/Alarms';
import Devices from './pages/Devices';
import Metrics from './pages/Metrics';
import './App.css';

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(true);

  return (
    <Router>
      <div className="app-container">
        <nav className={`sidebar ${sidebarOpen ? 'open' : 'closed'}`}>  
          <div className="logo">
            <h1>IT Ops Hub</h1>
          </div>
          <ul className="nav-menu">
            <li><Link to="/">Dashboard</Link></li>
            <li><Link to="/events">Events</Link></li>
            <li><Link to="/alarms">Alarms</Link></li>
            <li><Link to="/devices">Devices</Link></li>
            <li><Link to="/metrics">Metrics</Link></li>
          </ul>
        </nav>

        <div className="main-content">
          <header className="top-bar">
            <button onClick={() => setSidebarOpen(!sidebarOpen)}>☰</button>
            <h2>IT Operations Management</h2>
            <div className="user-profile">
              <span>Admin</span>
            </div>
          </header>

          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/events" element={<Events />} />
            <Route path="/alarms" element={<Alarms />} />
            <Route path="/devices" element={<Devices />} />
            <Route path="/metrics" element={<Metrics />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;