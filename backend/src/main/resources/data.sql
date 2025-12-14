INSERT INTO books (isbn, title, author, genre, synopsis, image_url, stock) 
VALUES 
('978-2-07-054120-7', 'Harry Potter à l''école des sorciers', 'J.K. Rowling', 'Fantastique', 'Le jeune Harry découvre qu''il est un sorcier...', 'https://m.media-amazon.com/images/I/81YOuOGFCJL.jpg', 5),
('978-2-266-15410-3', 'Le Seigneur des Anneaux', 'J.R.R. Tolkien', 'Fantasy', 'L''histoire de Frodon et de l''Anneau unique...', 'https://m.media-amazon.com/images/I/91jBdaRVqML.jpg', 3),
('978-0-452-28423-4', '1984', 'George Orwell', 'Dystopie', 'Dans un monde totalitaire...', 'https://m.media-amazon.com/images/I/71kxa1-0mfL.jpg', 2)
ON CONFLICT (isbn) DO NOTHING;  

-- Supprime et réinsère pour être sûr
DELETE FROM users WHERE username = 'admin';

INSERT INTO users (id, username, email, password, is_admin) 
VALUES (1, 'admin', 'admin@biblio.fr', '$2a$10$R0Vhlt/dafe.IslLByhNfunQzLgcmH154mTcUPocEgn9PnOMi9giq', true)
ON CONFLICT (id) DO UPDATE SET username = EXCLUDED.username;