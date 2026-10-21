/**
 * Experiment 11: Single Page Application (SPA) using React.
 * Concepts Demonstrated: React Hooks (useState, useEffect), Router (hash-based SPA navigation),
 * Component Props, and State Management.
 */

const { useState, useEffect } = React;

// Sample Fleet Data
const INITIAL_TRAINS = [
    { number: "12051", name: "Jan Shatabdi Express", from: "CSMT Mumbai", to: "Madgaon (Goa)", dept: "05:10", arr: "14:30", fareSL: 320, fareAC: 1050, available: 42, status: "ON TIME" },
    { number: "22221", name: "Rajdhani Express", from: "CSMT Mumbai", to: "Hazrat Nizamuddin", dept: "16:00", arr: "09:55", fareSL: 650, fareAC: 2450, available: 18, status: "10m LATE" },
    { number: "12123", name: "Deccan Queen Express", from: "CSMT Mumbai", to: "Pune Junction", dept: "17:10", arr: "20:25", fareSL: 120, fareAC: 480, available: 124, status: "ON TIME" },
    { number: "11019", name: "Konark Express", from: "CSMT Mumbai", to: "Bhubaneswar", dept: "14:00", arr: "04:15", fareSL: 580, fareAC: 1980, available: 8, status: "25m LATE" }
];

// Component 1: Navigation Header (Receives Props: activeTab, onNavigate)
function SPANavbar({ activeTab, onNavigate }) {
    const navItems = [
        { id: "home", label: "🏠 Home" },
        { id: "search", label: "🚆 Find Trains" },
        { id: "telemetry", label: "📡 Live TrackPulse" },
        { id: "pnr", label: "🎫 PNR Status" },
        { id: "experiments", label: "🧪 Lab Experiments" }
    ];

    return (
        <header style={{
            background: 'linear-gradient(90deg, #0f172a 0%, #1e293b 100%)',
            padding: '16px 32px',
            borderBottom: '1px solid #334155',
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
            flexWrap: 'wrap',
            gap: '12px'
        }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                <span style={{ fontSize: '1.8rem' }}>🚆</span>
                <div>
                    <h2 style={{ margin: 0, color: '#38bdf8', fontSize: '1.4rem' }}>RailRush <span style={{ color: '#f97316' }}>SPA</span></h2>
                    <small style={{ color: '#94a3b8' }}>Exp 11: React Hooks, Router, Props &amp; States</small>
                </div>
            </div>

            <nav style={{ display: 'flex', gap: '8px', flexWrap: 'wrap' }}>
                {navItems.map(item => (
                    <button
                        key={item.id}
                        onClick={() => onNavigate(item.id)}
                        style={{
                            background: activeTab === item.id ? '#f97316' : '#1e293b',
                            color: '#ffffff',
                            border: '1px solid ' + (activeTab === item.id ? '#f97316' : '#475569'),
                            padding: '8px 16px',
                            borderRadius: '8px',
                            fontWeight: '600',
                            cursor: 'pointer',
                            transition: 'all 0.2s ease'
                        }}
                    >
                        {item.label}
                    </button>
                ))}
            </nav>
        </header>
    );
}

// Component 2: Train Card (Demonstrates Props passing)
function TrainCard({ train, onBook }) {
    return (
        <div style={{
            background: '#1e293b',
            border: '1px solid #334155',
            borderRadius: '12px',
            padding: '20px',
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'space-between',
            boxShadow: '0 4px 12px rgba(0,0,0,0.2)'
        }}>
            <div>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '10px' }}>
                    <h3 style={{ margin: 0, color: '#38bdf8', fontSize: '1.2rem' }}>{train.name}</h3>
                    <span style={{
                        background: train.status === 'ON TIME' ? '#14532d' : '#7f1d1d',
                        color: train.status === 'ON TIME' ? '#4ade80' : '#fca5a5',
                        padding: '3px 8px',
                        borderRadius: '4px',
                        fontSize: '0.8rem',
                        fontWeight: 'bold'
                    }}>
                        {train.status}
                    </span>
                </div>
                <div style={{ color: '#94a3b8', fontSize: '0.9rem', marginBottom: '12px' }}>
                    Train #{train.number} | {train.from} &rarr; {train.to}
                </div>
                <div style={{ display: 'flex', justifyContent: 'space-between', background: '#0f172a', padding: '10px', borderRadius: '8px', marginBottom: '12px' }}>
                    <div>
                        <div style={{ fontSize: '0.75rem', color: '#64748b' }}>DEPARTURE</div>
                        <div style={{ fontSize: '1.1rem', fontWeight: 'bold', color: '#f8fafc' }}>{train.dept}</div>
                    </div>
                    <div style={{ textAlign: 'center', color: '#64748b', fontSize: '0.8rem', alignSelf: 'center' }}>
                        &mdash; Express &mdash;
                    </div>
                    <div style={{ textAlign: 'right' }}>
                        <div style={{ fontSize: '0.75rem', color: '#64748b' }}>ARRIVAL</div>
                        <div style={{ fontSize: '1.1rem', fontWeight: 'bold', color: '#f8fafc' }}>{train.arr}</div>
                    </div>
                </div>
            </div>

            <div>
                <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '12px', fontSize: '0.9rem' }}>
                    <span>Sleeper: <strong>&#8377;{train.fareSL}</strong></span>
                    <span>AC Tier: <strong>&#8377;{train.fareAC}</strong></span>
                    <span style={{ color: train.available > 10 ? '#4ade80' : '#f59e0b' }}>
                        Seats: {train.available}
                    </span>
                </div>
                <button
                    onClick={() => onBook(train)}
                    style={{
                        width: '100%',
                        background: '#0284c7',
                        color: '#ffffff',
                        border: 'none',
                        padding: '10px',
                        borderRadius: '6px',
                        fontWeight: 'bold',
                        cursor: 'pointer'
                    }}
                >
                    Reserve Seats &rarr;
                </button>
            </div>
        </div>
    );
}

// Main SPA Application Component (Demonstrates State & Hooks)
function RailRushSPA() {
    const [currentRoute, setCurrentRoute] = useState("home");
    const [trains, setTrains] = useState(INITIAL_TRAINS);
    const [selectedTrain, setSelectedTrain] = useState(null);
    const [searchQuery, setSearchQuery] = useState("");
    const [pnrInput, setPnrInput] = useState("");
    const [pnrResult, setPnrResult] = useState(null);
    const [passengerName, setPassengerName] = useState("");
    const [passengerAge, setPassengerAge] = useState("");
    const [travelClass, setTravelClass] = useState("SLEEPER");
    const [bookingSuccess, setBookingSuccess] = useState(null);
    const [tick, setTick] = useState(0);

    // useEffect hook to simulate live telemetry timer every 3 seconds
    useEffect(() => {
        const interval = setInterval(() => {
            setTick(prev => prev + 1);
        }, 3000);
        return () => clearInterval(interval);
    }, []);

    // Filter trains using state
    const filteredTrains = trains.filter(t =>
        t.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        t.from.toLowerCase().includes(searchQuery.toLowerCase()) ||
        t.to.toLowerCase().includes(searchQuery.toLowerCase()) ||
        t.number.includes(searchQuery)
    );

    // Booking Handler
    const handleCompleteBooking = (e) => {
        e.preventDefault();
        if (!passengerName || !passengerAge) {
            alert("Please complete passenger name and age.");
            return;
        }

        const generatedPNR = "RR" + Math.floor(10000000 + Math.random() * 90000000);
        const fare = travelClass === "SLEEPER" ? selectedTrain.fareSL : selectedTrain.fareAC;

        // Update train seat state
        setTrains(prevTrains =>
            prevTrains.map(t =>
                t.number === selectedTrain.number ? { ...t, available: Math.max(0, t.available - 1) } : t
            )
        );

        setBookingSuccess({
            pnr: generatedPNR,
            passengerName,
            passengerAge,
            trainName: selectedTrain.name,
            trainNumber: selectedTrain.number,
            travelClass,
            fare,
            date: new Date().toLocaleDateString()
        });

        setSelectedTrain(null);
        setPassengerName("");
        setPassengerAge("");
    };

    // PNR Search Handler
    const handleCheckPNR = (e) => {
        e.preventDefault();
        if (!pnrInput.trim()) {
            alert("Enter a PNR number to search.");
            return;
        }

        if (bookingSuccess && bookingSuccess.pnr === pnrInput.trim()) {
            setPnrResult(bookingSuccess);
        } else {
            setPnrResult({
                pnr: pnrInput.trim(),
                passengerName: "Aarya Chavan",
                passengerAge: 20,
                trainName: "12051 Jan Shatabdi Express",
                trainNumber: "12051",
                travelClass: "AC 3 TIER",
                fare: 1050,
                status: "CONFIRMED (Coach B2 - Berth 31)",
                date: "2026-10-25"
            });
        }
    };

    return (
        <div style={{ minHeight: '100vh', background: '#0b1329', color: '#f8fafc', fontFamily: "'Segoe UI', sans-serif" }}>
            <SPANavbar activeTab={currentRoute} onNavigate={(route) => { setCurrentRoute(route); setBookingSuccess(null); }} />

            <main style={{ padding: '32px', maxWidth: '1200px', margin: '0 auto' }}>
                {/* Route 1: Home View */}
                {currentRoute === "home" && (
                    <div>
                        <div style={{
                            background: 'linear-gradient(135deg, #1e3c72 0%, #2a5298 100%)',
                            borderRadius: '16px',
                            padding: '40px',
                            textAlign: 'center',
                            marginBottom: '32px',
                            boxShadow: '0 8px 30px rgba(0,0,0,0.4)'
                        }}>
                            <h1 style={{ fontSize: '2.4rem', margin: '0 0 12px', color: '#ffffff' }}>
                                🚆 RailRush Single Page Application
                            </h1>
                            <p style={{ fontSize: '1.15rem', color: '#cbd5e1', maxWidth: '700px', margin: '0 auto 24px' }}>
                                A modern, client-side routed railway ticket booking and telemetry portal built strictly with
                                React Functional Components, Hooks (<code>useState</code>, <code>useEffect</code>), Props, and State Management.
                            </p>
                            <div style={{ display: 'flex', gap: '16px', justifyContent: 'center' }}>
                                <button
                                    onClick={() => setCurrentRoute("search")}
                                    style={{
                                        background: '#f97316',
                                        color: '#ffffff',
                                        border: 'none',
                                        padding: '12px 28px',
                                        borderRadius: '8px',
                                        fontWeight: 'bold',
                                        cursor: 'pointer',
                                        fontSize: '1rem'
                                    }}
                                >
                                    Search Active Trains &rarr;
                                </button>
                                <button
                                    onClick={() => setCurrentRoute("telemetry")}
                                    style={{
                                        background: 'rgba(255,255,255,0.15)',
                                        color: '#ffffff',
                                        border: '1px solid rgba(255,255,255,0.3)',
                                        padding: '12px 28px',
                                        borderRadius: '8px',
                                        fontWeight: 'bold',
                                        cursor: 'pointer',
                                        fontSize: '1rem'
                                    }}
                                >
                                    Live TrackPulse Feeds
                                </button>
                            </div>
                        </div>

                        {/* Feature Cards Grid */}
                        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '20px' }}>
                            <div style={{ background: '#162447', padding: '24px', borderRadius: '12px', borderLeft: '4px solid #38bdf8' }}>
                                <h3>⚡ Reactive State Architecture</h3>
                                <p style={{ color: '#94a3b8' }}>State mutations trigger instantaneous sub-tree updates without full browser reloading.</p>
                            </div>
                            <div style={{ background: '#162447', padding: '24px', borderRadius: '12px', borderLeft: '4px solid #f97316' }}>
                                <h3>📦 Decoupled Props Delegation</h3>
                                <p style={{ color: '#94a3b8' }}>Train model objects and callbacks are routed down to modular view components via clean React Props.</p>
                            </div>
                            <div style={{ background: '#162447', padding: '24px', borderRadius: '12px', borderLeft: '4px solid #4ade80' }}>
                                <h3>🧭 Client-Side SPA Router</h3>
                                <p style={{ color: '#94a3b8' }}>Navigation switches cleanly among Home, Search, Telemetry, and PNR modules within one single document lifecycle.</p>
                            </div>
                        </div>
                    </div>
                )}

                {/* Route 2: Search & Booking View */}
                {currentRoute === "search" && (
                    <div>
                        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '24px', flexWrap: 'wrap', gap: '16px' }}>
                            <div>
                                <h2 style={{ margin: 0, color: '#38bdf8' }}>Find Available Trains</h2>
                                <p style={{ color: '#94a3b8', margin: '4px 0 0' }}>Search by train number, name, origin, or destination station</p>
                            </div>
                            <input
                                type="text"
                                placeholder="Search e.g. Goa, Rajdhani, CSMT..."
                                value={searchQuery}
                                onChange={(e) => setSearchQuery(e.target.value)}
                                style={{
                                    background: '#1e293b',
                                    border: '1px solid #475569',
                                    color: '#ffffff',
                                    padding: '10px 18px',
                                    borderRadius: '8px',
                                    minWidth: '280px'
                                }}
                            />
                        </div>

                        {bookingSuccess && (
                            <div style={{
                                background: '#14532d',
                                border: '1px solid #22c55e',
                                borderRadius: '12px',
                                padding: '20px',
                                marginBottom: '24px'
                            }}>
                                <h3 style={{ margin: '0 0 8px', color: '#4ade80' }}>🎉 Ticket Confirmed Successfully!</h3>
                                <p style={{ margin: '0 0 6px' }}>PNR Number: <strong>{bookingSuccess.pnr}</strong> | Passenger: {bookingSuccess.passengerName} (Age: {bookingSuccess.passengerAge})</p>
                                <p style={{ margin: 0 }}>Train: {bookingSuccess.trainName} | Class: {bookingSuccess.travelClass} | Total Fare: &#8377;{bookingSuccess.fare}</p>
                            </div>
                        )}

                        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '20px' }}>
                            {filteredTrains.map(train => (
                                <TrainCard
                                    key={train.number}
                                    train={train}
                                    onBook={(t) => setSelectedTrain(t)}
                                />
                            ))}
                        </div>

                        {/* Booking Modal / Overlay */}
                        {selectedTrain && (
                            <div style={{
                                position: 'fixed',
                                top: 0,
                                left: 0,
                                right: 0,
                                bottom: 0,
                                background: 'rgba(0,0,0,0.7)',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                padding: '20px',
                                zIndex: 1000
                            }}>
                                <div style={{
                                    background: '#1e293b',
                                    borderRadius: '16px',
                                    padding: '28px',
                                    maxWidth: '480px',
                                    width: '100%',
                                    border: '1px solid #475569'
                                }}>
                                    <h3 style={{ marginTop: 0, color: '#38bdf8' }}>Reserve Berth: {selectedTrain.name}</h3>
                                    <p style={{ color: '#94a3b8', fontSize: '0.9rem' }}>Train #{selectedTrain.number} | {selectedTrain.from} &rarr; {selectedTrain.to}</p>

                                    <form onSubmit={handleCompleteBooking}>
                                        <div style={{ marginBottom: '14px' }}>
                                            <label style={{ display: 'block', marginBottom: '6px', fontSize: '0.9rem' }}>Passenger Name</label>
                                            <input
                                                type="text"
                                                required
                                                value={passengerName}
                                                onChange={(e) => setPassengerName(e.target.value)}
                                                style={{ width: '100%', padding: '10px', background: '#0f172a', border: '1px solid #475569', color: '#fff', borderRadius: '6px' }}
                                                placeholder="e.g. Aarya Chavan"
                                            />
                                        </div>

                                        <div style={{ marginBottom: '14px' }}>
                                            <label style={{ display: 'block', marginBottom: '6px', fontSize: '0.9rem' }}>Passenger Age</label>
                                            <input
                                                type="number"
                                                required
                                                min="1"
                                                max="120"
                                                value={passengerAge}
                                                onChange={(e) => setPassengerAge(e.target.value)}
                                                style={{ width: '100%', padding: '10px', background: '#0f172a', border: '1px solid #475569', color: '#fff', borderRadius: '6px' }}
                                                placeholder="e.g. 21"
                                            />
                                        </div>

                                        <div style={{ marginBottom: '20px' }}>
                                            <label style={{ display: 'block', marginBottom: '6px', fontSize: '0.9rem' }}>Travel Class</label>
                                            <select
                                                value={travelClass}
                                                onChange={(e) => setTravelClass(e.target.value)}
                                                style={{ width: '100%', padding: '10px', background: '#0f172a', border: '1px solid #475569', color: '#fff', borderRadius: '6px' }}
                                            >
                                                <option value="SLEEPER">Sleeper (SL) - &#8377;{selectedTrain.fareSL}</option>
                                                <option value="AC_3_TIER">AC 3-Tier (3A) - &#8377;{selectedTrain.fareAC}</option>
                                            </select>
                                        </div>

                                        <div style={{ display: 'flex', gap: '10px' }}>
                                            <button
                                                type="submit"
                                                style={{ flex: 1, background: '#22c55e', color: '#fff', border: 'none', padding: '12px', borderRadius: '6px', fontWeight: 'bold', cursor: 'pointer' }}
                                            >
                                                Confirm Booking
                                            </button>
                                            <button
                                                type="button"
                                                onClick={() => setSelectedTrain(null)}
                                                style={{ background: '#475569', color: '#fff', border: 'none', padding: '12px 20px', borderRadius: '6px', cursor: 'pointer' }}
                                            >
                                                Cancel
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        )}
                    </div>
                )}

                {/* Route 3: Live Telemetry */}
                {currentRoute === "telemetry" && (
                    <div>
                        <div style={{ marginBottom: '24px' }}>
                            <h2 style={{ margin: 0, color: '#38bdf8' }}>📡 TrackPulse Real-Time Telemetry Feed</h2>
                            <p style={{ color: '#94a3b8', margin: '4px 0 0' }}>Live status simulated via React <code>useEffect</code> timer (Pulse #{tick})</p>
                        </div>

                        <div style={{ background: '#1e293b', borderRadius: '12px', padding: '24px', border: '1px solid #334155' }}>
                            <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
                                <thead>
                                    <tr style={{ borderBottom: '2px solid #334155', color: '#94a3b8' }}>
                                        <th style={{ padding: '12px' }}>Train #</th>
                                        <th style={{ padding: '12px' }}>Name</th>
                                        <th style={{ padding: '12px' }}>Current Location</th>
                                        <th style={{ padding: '12px' }}>Next En-Route Station</th>
                                        <th style={{ padding: '12px' }}>Running Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {trains.map((t, idx) => (
                                        <tr key={t.number} style={{ borderBottom: '1px solid #334155' }}>
                                            <td style={{ padding: '14px', fontWeight: 'bold' }}>{t.number}</td>
                                            <td style={{ padding: '14px', color: '#38bdf8' }}>{t.name}</td>
                                            <td style={{ padding: '14px' }}>{idx % 2 === 0 ? "Kalyan Jn (Platform 4)" : "Ratnagiri Station (Platform 2)"}</td>
                                            <td style={{ padding: '14px' }}>{idx % 2 === 0 ? "Igatpuri" : "Madgaon"}</td>
                                            <td style={{ padding: '14px' }}>
                                                <span style={{
                                                    background: t.status === 'ON TIME' ? '#14532d' : '#7f1d1d',
                                                    color: t.status === 'ON TIME' ? '#4ade80' : '#fca5a5',
                                                    padding: '4px 10px',
                                                    borderRadius: '4px',
                                                    fontSize: '0.85rem',
                                                    fontWeight: 'bold'
                                                }}>
                                                    {t.status}
                                                </span>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                )}

                {/* Route 4: PNR Status */}
                {currentRoute === "pnr" && (
                    <div>
                        <div style={{ maxWidth: '600px', margin: '0 auto' }}>
                            <div style={{ background: '#1e293b', padding: '28px', borderRadius: '12px', border: '1px solid #334155', marginBottom: '24px' }}>
                                <h2 style={{ marginTop: 0, color: '#38bdf8' }}>🎫 PNR Journey Inquiry</h2>
                                <p style={{ color: '#94a3b8', fontSize: '0.95rem' }}>Enter any 10-digit PNR code or query recent reservations.</p>

                                <form onSubmit={handleCheckPNR} style={{ display: 'flex', gap: '10px' }}>
                                    <input
                                        type="text"
                                        placeholder="e.g. RR84920194 or 8472910384"
                                        value={pnrInput}
                                        onChange={(e) => setPnrInput(e.target.value)}
                                        style={{ flex: 1, padding: '12px', background: '#0f172a', border: '1px solid #475569', color: '#fff', borderRadius: '6px' }}
                                    />
                                    <button
                                        type="submit"
                                        style={{ background: '#f97316', color: '#fff', border: 'none', padding: '12px 24px', borderRadius: '6px', fontWeight: 'bold', cursor: 'pointer' }}
                                    >
                                        Check
                                    </button>
                                </form>
                            </div>

                            {pnrResult && (
                                <div style={{ background: '#0f172a', padding: '24px', borderRadius: '12px', border: '1px solid #38bdf8' }}>
                                    <div style={{ display: 'flex', justifyContent: 'space-between', borderBottom: '1px solid #334155', paddingBottom: '12px', marginBottom: '14px' }}>
                                        <h3 style={{ margin: 0, color: '#38bdf8' }}>PNR: {pnrResult.pnr}</h3>
                                        <span style={{ color: '#4ade80', fontWeight: 'bold' }}>{pnrResult.status || "CONFIRMED"}</span>
                                    </div>
                                    <p><strong>Passenger:</strong> {pnrResult.passengerName} (Age: {pnrResult.passengerAge})</p>
                                    <p><strong>Train:</strong> {pnrResult.trainName}</p>
                                    <p><strong>Class:</strong> {pnrResult.travelClass} | <strong>Total Fare:</strong> &#8377;{pnrResult.fare}</p>
                                    <p style={{ margin: 0, color: '#94a3b8', fontSize: '0.85rem' }}>Journey Date: {pnrResult.date}</p>
                                </div>
                            )}
                        </div>
                    </div>
                )}

                {/* Route 5: Experiments Overview Link */}
                {currentRoute === "experiments" && (
                    <div style={{ textAlign: 'center', background: '#1e293b', padding: '40px', borderRadius: '16px', border: '1px solid #334155' }}>
                        <h2 style={{ color: '#38bdf8' }}>🔬 Full Stack Java Laboratory Suite (Exp 01 - 13)</h2>
                        <p style={{ color: '#94a3b8', maxWidth: '650px', margin: '0 auto 24px' }}>
                            This Single Page Application fulfills <strong>Experiment 11</strong> (React Hooks, Router, Props &amp; States).
                            You can explore all other experiments including <strong>Experiment 10</strong> (React Hooks 4 Color Buttons),
                            <strong>Experiment 08</strong> (Email presence validator), <strong>Experiment 09</strong> (DOM Color Changer),
                            and <strong>Experiments 12 &amp; 13</strong> (Spring Boot Monolith &amp; REST APIs) in the master lab suite!
                        </p>
                        <a
                            href="experiments.jsp"
                            style={{
                                display: 'inline-block',
                                background: '#f97316',
                                color: '#fff',
                                textDecoration: 'none',
                                padding: '12px 28px',
                                borderRadius: '8px',
                                fontWeight: 'bold'
                            }}
                        >
                            Open Master Lab Suite (experiments.jsp) &rarr;
                        </a>
                    </div>
                )}
            </main>
        </div>
    );
}

// Mount SPA
document.addEventListener("DOMContentLoaded", function() {
    var rootElement = document.getElementById("reactSPARoot");
    if (rootElement && window.ReactDOM) {
        var root = ReactDOM.createRoot(rootElement);
        root.render(React.createElement(RailRushSPA));
    }
});
