import React, { useEffect, useState } from 'react';
import '../styles/Pages.css';

interface Event {
  id: number;
  eventId: string;
  deviceId: string;
  eventType: string;
  severity: string;
  description: string;
  status: string;
  createdAt: string;
}

const Events: React.FC = () => {
  const [events, setEvents] = useState<Event[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const response = await fetch('http://localhost:8081/api/v1/events');
        const data = await response.json();
        setEvents(data);
      } catch (err) {
        console.error('Failed to fetch events:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchEvents();
  }, []);

  if (loading) return <div>Loading events...</div>;

  return (
    <div className="page-container">
      <h1>Events</h1>
      <table className="data-table">
        <thead>
          <tr>
            <th>Event ID</th>
            <th>Device ID</th>
            <th>Type</th>
            <th>Severity</th>
            <th>Status</th>
            <th>Created At</th>
          </tr>
        </thead>
        <tbody>
          {events.map((event) => (
            <tr key={event.id}>
              <td>{event.eventId}</td>
              <td>{event.deviceId}</td>
              <td>{event.eventType}</td>
              <td><span className={`severity ${event.severity.toLowerCase()}`}>{event.severity}</span></td>
              <td>{event.status}</td>
              <td>{new Date(event.createdAt).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Events;