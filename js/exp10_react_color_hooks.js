/**
 * Experiment 10: React Hooks Color Selector Component.
 * Syllabus Requirement: Program for making use of React Hooks that displays four buttons
 * namely, "Red", "Blue", "Green", "Yellow". On clicking any of these buttons, the code
 * displays the message that you have selected that particular color.
 */

const { useState } = React;

function RailwayColorHookDemo() {
    const [selectedColor, setSelectedColor] = useState(null);
    const [clickCount, setClickCount] = useState(0);

    const buttonConfig = [
        { name: "Red", bg: "#ef4444", text: "#ffffff", signal: "Danger / Stop Signal" },
        { name: "Blue", bg: "#3b82f6", text: "#ffffff", signal: "Special Freight / Shunting" },
        { name: "Green", bg: "#22c55e", text: "#ffffff", signal: "Clear / High-Speed Track" },
        { name: "Yellow", bg: "#eab308", text: "#000000", signal: "Caution / Approach Station" }
    ];

    const handleColorClick = (colorName) => {
        setSelectedColor(colorName);
        setClickCount(prev => prev + 1);
    };

    const resetSelection = () => {
        setSelectedColor(null);
    };

    return (
        <div style={{
            background: '#131b2e',
            padding: '24px',
            borderRadius: '12px',
            border: '1px solid #1f2d4d',
            maxWidth: '650px',
            margin: '0 auto',
            color: '#ffffff',
            boxShadow: '0 6px 20px rgba(0,0,0,0.35)'
        }}>
            <h3 style={{ marginTop: 0, color: '#38bdf8', fontSize: '1.4rem' }}>
                🚦 React Hooks Color Selection Portal
            </h3>
            <p style={{ color: '#94a3b8', fontSize: '0.95rem', marginBottom: '1.2rem' }}>
                Implemented using React Functional Components and the <code>useState</code> Hook.
            </p>

            <div style={{
                display: 'flex',
                gap: '12px',
                flexWrap: 'wrap',
                marginBottom: '20px'
            }}>
                {buttonConfig.map((item) => (
                    <button
                        key={item.name}
                        onClick={() => handleColorClick(item.name)}
                        style={{
                            backgroundColor: item.bg,
                            color: item.text,
                            border: selectedColor === item.name ? '3px solid #ffffff' : 'none',
                            padding: '12px 24px',
                            fontSize: '1rem',
                            fontWeight: 'bold',
                            borderRadius: '8px',
                            cursor: 'pointer',
                            boxShadow: selectedColor === item.name ? '0 0 15px ' + item.bg : 'none',
                            transition: 'transform 0.15s ease, box-shadow 0.2s ease',
                            transform: selectedColor === item.name ? 'scale(1.06)' : 'scale(1)'
                        }}
                    >
                        {item.name}
                    </button>
                ))}
            </div>

            {selectedColor ? (
                <div style={{
                    padding: '16px',
                    borderRadius: '8px',
                    backgroundColor: '#1e293b',
                    borderLeft: '5px solid ' + (
                        selectedColor === 'Red' ? '#ef4444' :
                        selectedColor === 'Blue' ? '#3b82f6' :
                        selectedColor === 'Green' ? '#22c55e' : '#eab308'
                    ),
                    marginTop: '15px'
                }}>
                    <p style={{ margin: '0 0 6px', fontSize: '1.15rem', fontWeight: 600 }}>
                        📢 <strong>You have selected that particular color: <span style={{ color: selectedColor === 'Yellow' ? '#fde047' : selectedColor }}>{selectedColor}</span></strong>
                    </p>
                    <p style={{ margin: '0 0 10px', fontSize: '0.9rem', color: '#cbd5e1' }}>
                        Railway Context: {buttonConfig.find(b => b.name === selectedColor)?.signal}
                    </p>
                    <small style={{ color: '#94a3b8' }}>Total Interactions: {clickCount}</small>
                    <button
                        onClick={resetSelection}
                        style={{
                            marginLeft: '15px',
                            background: 'transparent',
                            border: '1px solid #64748b',
                            color: '#94a3b8',
                            borderRadius: '4px',
                            padding: '4px 8px',
                            cursor: 'pointer',
                            fontSize: '0.8rem'
                        }}
                    >
                        Reset
                    </button>
                </div>
            ) : (
                <div style={{
                    padding: '12px',
                    borderRadius: '8px',
                    backgroundColor: '#0f172a',
                    border: '1px dashed #334155',
                    color: '#94a3b8',
                    fontStyle: 'italic',
                    fontSize: '0.95rem'
                }}>
                    Click any color button above to trigger the React useState Hook.
                </div>
            )}
        </div>
    );
}

// Mount if DOM container exists
document.addEventListener("DOMContentLoaded", function() {
    var container = document.getElementById("reactColorHooksRoot");
    if (container && window.ReactDOM) {
        var root = ReactDOM.createRoot(container);
        root.render(React.createElement(RailwayColorHookDemo));
    }
});
