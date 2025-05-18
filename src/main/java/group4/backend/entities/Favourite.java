package group4.backend.entities;

import jakarta.persistence.*;

/**
 * class representing favourite table in our database.
 */
@Entity
@Table(name = "favourite")
public class Favourite {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "favourite_id", nullable = false)
        private int favouriteId;

        @ManyToOne
        @JoinColumn(name = "room_id", nullable = false, foreignKey = @ForeignKey(name = "user_id_fk"))
        private Room room;

        @ManyToOne
        @JoinColumn(name = "username", nullable = false, foreignKey = @ForeignKey(name = "username"))
        private User user;

        /**
         * gets favourite
         *
         * @return the int favourite_id
         */
        public int getFavouriteId() {
            return favouriteId;
        }

        /**
         * sets favourite_id to new favourite_id
         *
         * @param favouriteId the new favouriteId
         */
        public void setFavouriteId(int favouriteId) {
            this.favouriteId = favouriteId;
        }

        /**
         * gets room_id
         *
         * @return the int room_id primary key
         */
        public Room getRoom() {
            return room;
        }

        /**
         * sets room_id to new room_id
         *
         * @param room the new roomId
         */
        public void setRoom(Room room) {
            this.room = room;
        }

        /**
         * gets username
         *
         * @return the string username
         */
        public User getUser() {
            return user;
        }

        /**
         * sets username to new username
         *
         * @param user the new username
         */
        public void setUser(User user) {
            this.user = user;
        }
    }

