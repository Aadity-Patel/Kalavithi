import React from "react";
import Button from "@mui/material/Button";
import "./Button.css";

function LandingButton({ buttonText, onClick }) {
  return (
    <div className="p-2">
      <Button
        className="button"
        data-testid={buttonText}
        onClick={onClick}
        variant="contained"
        size="medium"
      >
        {buttonText}
      </Button>
    </div>
  );
}

export default LandingButton;
