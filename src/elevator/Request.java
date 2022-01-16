/**
 * 
 */
package elevator;

/**
 * @author duchauha
 *
 */
public class Request {
     Direction direction;
     Location location;
     int currFloor;
     int desiredFloor;
     
     public Request(int currFloor, int desiredFloor, Direction direction, Location location) {
         this.currFloor = currFloor;
         this.desiredFloor = desiredFloor;
         this.direction = direction;
         this.location = location;
     }
     
}
