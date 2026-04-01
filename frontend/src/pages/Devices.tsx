import React, { useEffect, useState } from 'react';
import '../styles/Pages.css';

interface Device {
  id: string;
  name: string;
  type: string;
  status: string;
}

const Devices: React.FC = () => {
  const [devices, setDevices] = useState<Device[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDevices = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/v1/devices');
        const data = await response.json();
        setDevices(data);
      } catch (err) {
        console.error('Failed to fetch devices:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchDevices();
  }, []);

  if (loading) return <div>Loading devices...</div>;

  return (
    <div className="page-container">
      <h1>Devices</h1>
      <div className="devices-grid">
        {devices.map((device) => (
          <div key={device.id} className="device-card">
            <h3>{device.name}</h3>
            <p><strong>Type:</strong> {device.type}</p>
            <p><strong>Status:</strong> <span className={`status ${device.status.toLowerCase()}`}>{device.status}</span></p>
            <p><strong>ID:</strong> {device.id}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Devices;