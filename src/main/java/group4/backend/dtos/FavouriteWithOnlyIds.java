package group4.backend.dtos;

public class FavouriteWithOnlyIds {

  private Integer favouriteId;

  private Integer roomId;

  private String username;

  /**
   * gets favourite
   *
   * @return the int favourite_id
   */
  public Integer getFavouriteId() {
    return favouriteId;
  }

  /**
   * sets favourite_id to new favourite_id
   *
   * @param favouriteId the new favouriteId
   */
  public void setFavouriteId(Integer favouriteId) {
    this.favouriteId = favouriteId;
  }

  /**
   * gets room_id
   *
   * @return the int room_id primary key
   */
  public Integer getRoomId() {
    return roomId;
  }

  public void setRoom(Integer roomId) {
    this.roomId = roomId;
  }

  /**
   * gets username
   *
   * @return the string username
   */
  public String getUsername() {
    return username;
  }

  public void setUser(String username) {
    this.username = username;
  }
}
