import React from 'react';
import { render, screen } from '@testing-library/react';
import App from '../App';

describe('App Component', () => {
  test('renders welcome message', () => {
    render(<App />);
    const linkElement = screen.getByText(/welcome to the app/i);
    expect(linkElement).toBeInTheDocument();
  });

  test('renders learn react link', () => {
    render(<App />);
    const linkElement = screen.getByText(/learn react/i);
    expect(linkElement).toBeInTheDocument();
  });
});