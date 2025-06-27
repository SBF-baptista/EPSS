import React from 'react';
import { createRoot } from 'react-dom/client';
import KanbanBoard from './kanban';

const root = createRoot(document.getElementById('root'));
root.render(<KanbanBoard />);
