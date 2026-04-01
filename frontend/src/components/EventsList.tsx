import React from 'react';
import { Alarm, Event } from '../types';  // Adjust the import as needed

interface EventsListProps {
    events: Event[];
    alarms: Alarm[];
}

const EventsList: React.FC<EventsListProps> = ({ events, alarms }) => {
    return (
        <div>
            <h2>Events</h2>
            <ul>
                {events.map((event) => (
                    <li key={event.id}>{event.title} - {event.date}</li>
                ))}
            </ul>
            <h2>Alarms</h2>
            <ul>
                {alarms.map((alarm) => (
                    <li key={alarm.id}>{alarm.message} - {alarm.time}</li>
                ))}
            </ul>
        </div>
    );
};

export default EventsList;
