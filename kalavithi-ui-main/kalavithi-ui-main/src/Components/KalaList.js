import React, { useState, useEffect } from "react";
import "./KalaList.css";
import "../App.css";
import UserState from "./UserState";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";
import ImageListItemBar from "@mui/material/ImageListItemBar";
import LikeImage from "./LikeImage/LikeImage";
import BaseAPI from "./API";

const KalaList = (props) => {
  const [open, setOpen] = useState(false);
  const [width, setWidth] = useState(window.innerWidth);
  const [columnNumber, setColumnNumber] = useState(3);
  const [isHidden, setOverflow] = useState(false);
  const [imageArray, setImageArray] = useState([]);
  const [loading, setLoading] = useState(true);
  window.addEventListener("resize", function () {
    setWidth(window.innerWidth);
  });

  useEffect(() => {
    if (width < 550) {
      setColumnNumber(1);
    } else if (width < 770) {
      setColumnNumber(2);
    } else {
      setColumnNumber(3);
    }
  }, [width]);

  const handleClickOpen = () => {
    setOpen(UserState);
  };

  const handleClose = () => {
    setOpen(false);
  };
  const handleHiddenOverflow = () => {
    if (isHidden) setOverflow(false);
    else setOverflow(true);
  };

  useEffect(() => {
    if (localStorage.getItem("is-logged-in")) {
      handleSubmit();
    }
  }, [props.loggedIn]);

  async function getImageLike(){
    const imageResponse = await BaseAPI.likeCountURL();
    saveImageCount(imageResponse.data);
  }

  useEffect(()=>{
    getImageLike();
  },[])

  async function handleSubmit() {
    try {
      const userId = JSON.parse(localStorage.getItem("userId"));
      let response = await BaseAPI.likeColourURL(userId);
      if (response.status === 200) {
        setImageArray(response.data);
      }
    } catch (err) {
      console.log(err);
    }
  }

  function saveImageCount(imgCount) {
    imgCount.forEach((imgC) => {
      localStorage.setItem(imgC.image_id, imgC.count)
    });
    setLoading(false)
  }

  return (
    <>
    {loading===true?
    <>Loading</>
    :
      <ImageList
        variant="masonry"
        cols={columnNumber}
        gap={30}
        style={{ overflowX: "hidden" }}
      >
        {props.kalas.map((kala, index) => 
        (
          <ImageListItem key={index} className="img-container"
          >
            
            <img
              src={kala.url}
              data-testid="image-dialog"
              alt="kala"
              loading="lazy"
              className="imagelist-img"
              onClick={handleClickOpen}
            />
            <ImageListItemBar
              onClick={handleClickOpen}
              className="img-description"
              data-testid="img-desc"
              title={
                <div className="desc-content">
                  <p
                    className="img-description-p"
                    style={{
                      overflow: isHidden ? "visible" : "hidden",
                      whiteSpace: isHidden ? "normal" : "nowrap",
                    }}
                    onClick={handleHiddenOverflow}
                  >
                    {kala.description}
                  </p>
                  <LikeImage
                    id={kala.id}
                    color={imageArray.includes(kala.id) ? true : false}
                    data-testid="like-icon"
                    likeCount={localStorage.getItem(kala.id)}
                  />
                </div>
              }
            />
          </ImageListItem>
         ))
            
         }
      </ImageList>
      }
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle
          data-testid="alert-dialog-title"
          style={{ fontSize: "18px" }}
        >
          <b>Unauthorized Access</b>
        </DialogTitle>
        <DialogContent>
          <DialogContentText
            id="alert-dialog-description"
            style={{ fontSize: "14px" }}
          >
            Please login or register to view full image.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} autoFocus style={{ fontSize: "12px" }}>
            OK
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default KalaList;
