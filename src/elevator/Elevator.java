/**
 * 
 */
package elevator;

import java.util.PriorityQueue;

/**
 * @author duchauha
 *
 */
public class Elevator {
	
	int currFloor;
	Direction direction;
	PriorityQueue<Request> upQueue ;
	PriorityQueue<Request> downQueue;
	
	public Elevator(int currFloor) {
		this.currFloor = currFloor;
		this.direction = Direction.IDLE;
		//min heap
		upQueue = new PriorityQueue<>((a,b)->a.desiredFloor-b.desiredFloor);
		// max heap
		downQueue = new PriorityQueue<>((a,b)->b.desiredFloor-a.desiredFloor);
	}
	
	public void sendUpRequest(Request upRequest) {
		
		//if request is made outside of the elevator then first go the that particular floor first before going to the desired floor
		if(upRequest.location==Location.OUTSIDE_ELEVATOR) {
			upQueue.offer(new Request(upRequest.currFloor,upRequest.currFloor,Direction.UP,Location.OUTSIDE_ELEVATOR));
			System.out.println("Appending up request for floor "+upRequest.currFloor+" .");
		}
		
		// go the desired floor
		upQueue.offer(upRequest);
		System.out.println("Appending up request for floor "+upRequest.desiredFloor+" .");
	}
	
   public void sendDownRequest(Request downRequest) {
		
		//similar to up request we can have down request
		if(downRequest.location==Location.OUTSIDE_ELEVATOR) {
			downQueue.offer(new Request(downRequest.currFloor,downRequest.currFloor,Direction.DOWN,Location.OUTSIDE_ELEVATOR));
			System.out.println("Appending down request for floor "+downRequest.currFloor+" .");
		}
		
		// go the desired floor
		downQueue.offer(downRequest);
		System.out.println("Appending down request for floor "+downRequest.desiredFloor+" .");
	}
   
   public void run() {
	   //if any of the queue has request , keep processing it.
	   while(!upQueue.isEmpty() || !downQueue.isEmpty() ) {
		   processRequests();
	   }
	   
	   // move the state to idle
	   this.direction = Direction.IDLE;
	   System.out.println("Finished processing all the request");
   }
   
   private void processRequests() {
	   //assuming up request has priority over down request
	   if(this.direction==Direction.IDLE || this.direction==Direction.UP) {
		   processUpRequests();
		   processDownRequests();
	   }else {
		   processDownRequests();
		   processUpRequests();
		  
	   }
   }
   
   private void processUpRequests() {
	   
	   while(!upQueue.isEmpty()) {
		   Request upRequest = upQueue.poll();
		// talk to the hardware
		   this.currFloor = upRequest.desiredFloor;
		   System.out.println("processing up request. Elevator stopped at floor "+this.currFloor);
	   }
	   if(!downQueue.isEmpty()) {
		   this.direction = Direction.DOWN;
	   }else {
		   this.direction = Direction.IDLE;
	   }
	 
	   
   }
   
   private void processDownRequests() {
	   while(!downQueue.isEmpty()) {
		   Request downRequest = downQueue.poll();
		// talk to the hardware
		   this.currFloor = downRequest.desiredFloor;
		   System.out.println("processing down request. Elevator stopped at floor "+this.currFloor);
	   }
	   if(!upQueue.isEmpty()) {
		   this.direction = Direction.UP;
	   }else {
		   this.direction = Direction.IDLE;
	   }
	 
   }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Elevator elevator = new Elevator(0);
		
		//uprequests from the inside of the elevator
		Request upRequest1 = new Request(elevator.currFloor,6,Direction.UP,Location.INSIDE_ELEVATOR);
		Request upRequest2 = new Request(elevator.currFloor,2,Direction.UP,Location.INSIDE_ELEVATOR);
        
		//downrequests from the inside of the elevator
		Request downRequest1 = new Request(elevator.currFloor,3,Direction.DOWN,Location.INSIDE_ELEVATOR);
		Request downRequest2 = new Request(elevator.currFloor,1,Direction.DOWN,Location.INSIDE_ELEVATOR);
		
		//downrequest from outside of the elevator
		Request downRequest3 = new Request(4,0,Direction.DOWN,Location.OUTSIDE_ELEVATOR);
		
		 // Two people inside of the elevator pressed button to go up to floor 6 and 2.
        elevator.sendUpRequest(upRequest1);
        elevator.sendUpRequest(upRequest2);

        // One person outside of the elevator at floor 4 pressed button to go down to floor 0
        elevator.sendDownRequest(downRequest3);

        // Two person inside of the elevator pressed button to go down to floor 3 and 1.
        elevator.sendDownRequest(downRequest1);
        elevator.sendDownRequest(downRequest2);
        
		elevator.run();
		
	}

}
