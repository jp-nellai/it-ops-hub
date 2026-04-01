import React, { useEffect, useState } from 'react';

const MetricsDashboard: React.FC = () => {
    const [metrics, setMetrics] = useState<any>(null);

    useEffect(() => {
        const fetchMetrics = async () => {
            try {
                const response = await fetch('http://your-prometheus-api/metrics');
                const data = await response.json();
                setMetrics(data);
            } catch (error) {
                console.error('Error fetching metrics:', error);
            }
        };

        fetchMetrics();
    }, []);

    if (!metrics) {
        return <div>Loading metrics...</div>;
    }

    return (
        <div>
            <h1>Prometheus Metrics Dashboard</h1>
            <pre>{JSON.stringify(metrics, null, 2)}</pre>
        </div>
    );
};

export default MetricsDashboard;