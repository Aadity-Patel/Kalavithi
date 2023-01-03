CREATE TABLE IF NOT EXISTS likes (
    user_id int ,
    image_id int ,

    FOREIGN KEY (user_id) REFERENCES user_profile (id) ON DELETE CASCADE,
    FOREIGN KEY (image_id) REFERENCES image (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id,image_id)
);