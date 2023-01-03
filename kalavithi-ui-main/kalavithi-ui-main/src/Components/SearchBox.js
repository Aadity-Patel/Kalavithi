import React from "react";
import "../App.css";

const SearchBox = () => {
  return (
    <div className="col col-sm-4 pt-4 ">
        <input
        data-testid="search-input"
        className="form-control"
        placeholder="Type to Search"
        style={{fontSize:"14px",fontFamily: `"Roboto", "Helvetica", "Arial", sans-serif`,}}
      >
        
        </input>
        </div>
    
  );
};

export default SearchBox;
