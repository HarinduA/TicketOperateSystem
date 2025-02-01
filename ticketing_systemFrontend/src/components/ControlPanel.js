import React from "react";

const ControlPanel = ({ startSystem, stopSystem, resetSystem, fetchSavedConfig }) => {
  return (
    <div className="button-group">
      <button className="start-button" onClick={startSystem}>
        Start
      </button>
      <button className="stop-button" onClick={stopSystem}>
        Stop
      </button>
      <button className="reset-button" onClick={resetSystem}>
        Reset
      </button>
      <button className="save-config-button" onClick={fetchSavedConfig}>
        Load Saved Config
      </button>
    </div>
  );
};

export default ControlPanel;