import React, { useEffect, useState } from 'react';
import '../styles/Pages.css';

interface Metric {
  name: string;
  value: number;
  unit: string;
  timestamp: string;
}

const Metrics: React.FC = () => {
  const [metrics, setMetrics] = useState<Metric[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchMetrics = async () => {
      try {
        const response = await fetch('http://localhost:8082/api/v1/metrics');
        const data = await response.json();
        setMetrics(data);
      } catch (err) {
        console.error('Failed to fetch metrics:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchMetrics();
    const interval = setInterval(fetchMetrics, 10000);
    return () => clearInterval(interval);
  }, []);

  if (loading) return <div>Loading metrics...</div>;

  return (
    <div className="page-container">
      <h1>System Metrics</h1>
      <table className="data-table">
        <thead>
          <tr>
            <th>Metric Name</th>
            <th>Value</th>
            <th>Unit</th>
            <th>Last Updated</th>
          </tr>
        </thead>
        <tbody>
          {metrics.map((metric) => (
            <tr key={metric.name}>
              <td>{metric.name}</td>
              <td>{metric.value.toFixed(2)}</td>
              <td>{metric.unit}</td>
              <td>{new Date(metric.timestamp).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Metrics;