import React, { useState, useEffect } from "react";
import Typography from "@mui/material/Typography";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import FavoriteIcon from "@mui/icons-material/Favorite";
import "./LikeImage.css";
import BaseAPI from "../API";

const LikeImage = ({ id, color, likeCount }) => {
  const [isLiked, setIsLiked] = useState(color==undefined?0:color);
  const [count, setCount] = useState(likeCount);
  const [userId, setUserId] = useState([]);

  const handleFavoriteClicked = (imageId) => {
    let currentImageLiked = isLiked;
    setIsLiked(!currentImageLiked);
    if (!currentImageLiked) {
      setCount(parseInt(count) + 1);
    } else {
      setCount(parseInt(count) - 1);
    }
  };

  useEffect(() => {
    setIsLiked(color);
  }, [color]);

  useEffect(()=>{
    setCount(likeCount);

  },[likeCount])
  let handleSubmit = async () => {
    try {
      handleFavoriteClicked(id);
      const userId = JSON.parse(localStorage.getItem("userId"));
      if (userId) {
        setUserId(userId);
      }
      let res = await BaseAPI.likeImageURL(userId, id);
      if (res.status === 200) {
        console.log("User liked image");
      } else {
        console.log("Some error occured");
      }
    } catch (err) {
      console.log(err);
      setIsLiked(false);
    }
  };

  return (
    <div>
      <Typography variant="subtitle1" className="img-like-count">
        {count} &nbsp;
        {isLiked === false ? (
          localStorage.getItem("is-logged-in") ? (
            <FavoriteBorderIcon
              className="img-like-icon"
              onClick={handleSubmit}
              data-testid="unfilled-like-icon"
            />
          ) : (
            <FavoriteBorderIcon
              className="img-like-icon"
              data-testid="unfilled-like-icon"
            />
          )
        ) : (
          <FavoriteIcon
            className="img-like-icon-filled"
            onClick={handleSubmit}
            data-testid="filled-like-icon"
          />
        )}
      </Typography>
    </div>
  );
};

export default LikeImage;
