import React, { useState } from "react";
import UserState from "../UserState";
import ChangePasswordModal from "../ChangePassword/ChangePasswordModal";
import Typography from "@mui/material/Typography";
import { styled, alpha } from "@mui/material/styles";
import ListItemText from "@mui/material/ListItemText";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Tooltip from "@mui/material/Tooltip";
import IconButton from "@mui/material/IconButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import SettingsIcon from "@mui/icons-material/Settings";
import VpnKeyIcon from "@mui/icons-material/VpnKey";
import Logout from "@mui/icons-material/Logout";
const StyledMenu = styled((props) => (
  <Menu
    elevation={0}
    anchorOrigin={{
      vertical: "bottom",
      horizontal: "right",
    }}
    transformOrigin={{
      vertical: "top",
      horizontal: "right",
    }}
    {...props}
  />
))(({ theme }) => ({
  "& .MuiPaper-root": {
    borderRadius: 6,
    marginTop: theme.spacing(1),
    minWidth: 180,
    color:
      theme.palette.mode === "light"
        ? "rgb(55, 65, 81)"
        : theme.palette.grey[300],
    boxShadow:
      "rgb(255, 255, 255) 0px 0px 0px 0px, rgba(0, 0, 0, 0.05) 0px 0px 0px 1px, rgba(0, 0, 0, 0.1) 0px 10px 15px -3px, rgba(0, 0, 0, 0.05) 0px 4px 6px -2px",
    "& .MuiMenu-list": {
      padding: "4px 0",
    },
    "& .MuiMenuItem-root": {
      "& .MuiSvgIcon-root": {
        fontSize: 25,
        color: theme.palette.text.secondary,
        marginRight: theme.spacing(1.5),
      },
      "&:active": {
        backgroundColor: alpha(
          theme.palette.primary.main,
          theme.palette.action.selectedOpacity
        ),
      },
    },
  },
}));
function CustomizedMenus({ setLoggedIn }) {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [openPasswordPopUp, setOpenPasswordPopUp] = useState(false);
  const open = Boolean(anchorEl);
  function logout() {
    localStorage.clear();
    setLoggedIn(false);
    window.location.href = "/";
  }
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  return (
    <div>
      <React.Fragment>
        <Tooltip title="Account settings" placement="top">
          <IconButton
            className="settings"
            onClick={handleClick}
            size="small"
            sx={{ ml: 2 }}
            aria-controls={open ? "account-menu" : undefined}
            aria-haspopup="true"
            aria-expanded={open ? "true" : undefined}
          >
            <SettingsIcon
              
              data-testid="setting-icon"
              sx={{ width: 32, height: 32 }}
              style={{ color: "white" }}
            ></SettingsIcon>
          </IconButton>
        </Tooltip>
        <StyledMenu
          id="demo-customized-menu"
           MenuListProps={{
            "aria-labelledby": "demo-customized-button",
          }}
          anchorEl={anchorEl}
          open={open}
          onClose={handleClose}
          onClick={handleClose}
        >
          <MenuItem
            value="Change-Password"
            onClick={() => {
              handleClose();
              setOpenPasswordPopUp(true);
            }}
          >
            <ListItemIcon>
              <VpnKeyIcon fontSize="small" />
            </ListItemIcon>
            <ListItemText
              data-testid="change-password"
              disableTypography
              styles
              primary={
                <Typography type="body2" style={{ fontSize: "1.5em" }}>
                  Change Password
                </Typography>
              }
            />
          </MenuItem>
          <MenuItem
            value="Log-out"
            onClick={() => {
              logout();
              UserState();
            }}
          >
            <ListItemIcon>
              <Logout fontSize="small" />
            </ListItemIcon>
            <ListItemText
              data-testid="logout"
              disableTypography
              styles
              primary={
                <Typography type="body2" style={{ fontSize: "1.5em" }}>
                  Logout
                </Typography>
              }
            />
          </MenuItem>
        </StyledMenu>
        <ChangePasswordModal
          openState={openPasswordPopUp}
          setOpenState={setOpenPasswordPopUp}
        />
      </React.Fragment>
    </div>
  );
}
export default CustomizedMenus;
