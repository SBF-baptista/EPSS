import React, { useEffect, useState } from 'react';

const STATUSES = [
  'Novos pedidos',
  'Em produ\u00e7\u00e3o',
  'Aguardando envio',
  'Enviado',
  'Em Stand-by'
];

function KanbanBoard() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    fetch('/api/orders')
      .then(res => res.json())
      .then(setOrders)
      .catch(() => {});
  }, []);

  const move = (id, status) => {
    fetch(`/api/orders/${id}/status`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'text/plain' },
      body: status
    }).then(() => {
      setOrders(orders.map(o => o.id === id ? { ...o, status } : o));
    });
  };

  return (
    <div style={{ display: 'flex', gap: '1rem' }}>
      {STATUSES.map(s => (
        <div key={s} style={{ flex: 1 }}>
          <h3>{s}</h3>
          {orders.filter(o => o.status === s).map(o => (
            <div key={o.id} style={{ border: '1px solid #ccc', padding: '8px', marginBottom: '8px' }}>
              <p><strong>Pedido #{o.id}</strong></p>
              <p>Marca: {o.marca}</p>
              <p>Modelo: {o.modelo}</p>
              <p>Rastreador: {o.rastreador}</p>
              <p>Configura\u00e7\u00e3o: {o.configuracao}</p>
              <select value={o.status} onChange={e => move(o.id, e.target.value)}>
                {STATUSES.map(st => <option key={st} value={st}>{st}</option>)}
              </select>
            </div>
          ))}
        </div>
      ))}
    </div>
  );
}

export default KanbanBoard;
