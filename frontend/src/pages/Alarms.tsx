import React, { useEffect, useState } from 'react';
import '../styles/Pages.css';

interface Alarm {
  id: number;
  alarmId: string;
  deviceId: string;
  alarmName: string;
  severity: string;
  status: string;
  assignedTo: string;
  createdAt: string;
}

const Alarms: React.FC = () => {
  const [alarms, setAlarms] = useState<Alarm[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchAlarms = async () => {
      try {
        const response = await fetch('http://localhost:8081/api/v1/alarms');
        const data = await response.json();
        setAlarms(data);
      } catch (err) {
        console.error('Failed to fetch alarms:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchAlarms();
  }, []);

  if (loading) return <div>Loading alarms...</div>;

  return (
    <div className="page-container">
      <h1>Alarms</h1>
      <table className="data-table">
        <thead>
          <tr>
            <th>Alarm ID</th>
            <th>Name</th>
            <th>Device ID</th>
            <th>Severity</th>
            <th>Status</th>
            <th>Assigned To</th>
            <th>Created At</th>
          </tr>
        </thead>
        <tbody>
          {alarms.map((alarm) => (
            <tr key={alarm.id}>
              <td>{alarm.alarmId}</td>
              <td>{alarm.alarmName}</td>
              <td>{alarm.deviceId}</td>
              <td><span className={`severity ${alarm.severity.toLowerCase()}`}>{alarm.severity}</span></td>
              <td>{alarm.status}</td>
              <td>{alarm.assignedTo || 'Unassigned'}</td>
              <td>{new Date(alarm.createdAt).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Alarms;