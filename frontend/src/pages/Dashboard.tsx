import React, { useEffect, useState } from 'react';
import '../styles/Dashboard.css';

interface SystemStatus {
  status: string;
  cpuUsage: number;
  memoryUsage: number;
  diskUsage: number;
}

const Dashboard: React.FC = () => {
  const [systemStatus, setSystemStatus] = useState<SystemStatus | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchSystemStatus = async () => {
      try {
        const response = await fetch('http://localhost:8082/api/v1/metrics/health/system');
        const data = await response.json();
        setSystemStatus(data);
      } catch (err) {
        setError('Failed to fetch system status');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchSystemStatus();
    const interval = setInterval(fetchSystemStatus, 5000);
    return () => clearInterval(interval);
  }, []);

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="dashboard">
      <h1>Dashboard</h1>
      
      <div className="status-overview">
        <h2>System Status: <span className={`status ${systemStatus?.status.toLowerCase()}`}>{systemStatus?.status}</span></h2>
      </div>

      <div className="metrics-grid">
        <div className="metric-card">
          <h3>CPU Usage</h3>
          <div className="metric-value">{systemStatus?.cpuUsage.toFixed(1)}%</div>
          <div className="metric-bar">
            <div className="progress" style={{ width: `${systemStatus?.cpuUsage}%` }}></div>
          </div>
        </div>

        <div className="metric-card">
          <h3>Memory Usage</h3>
          <div className="metric-value">{systemStatus?.memoryUsage.toFixed(1)}%</div>
          <div className="metric-bar">
            <div className="progress" style={{ width: `${systemStatus?.memoryUsage}%` }}></div>
          </div>
        </div>

        <div className="metric-card">
          <h3>Disk Usage</h3>
          <div className="metric-value">{systemStatus?.diskUsage.toFixed(1)}%</div>
          <div className="metric-bar">
            <div className="progress" style={{ width: `${systemStatus?.diskUsage}%` }}></div>
          </div>
        </div>
      </div>

      <div className="quick-stats">
        <div className="stat-item">
          <span>Active Devices</span>
          <span className="stat-number">12</span>
        </div>
        <div className="stat-item">
          <span>Open Alarms</span>
          <span className="stat-number warning">3</span>
        </div>
        <div className="stat-item">
          <span>Recent Events</span>
          <span className="stat-number">45</span>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;