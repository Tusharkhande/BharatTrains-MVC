function addRouteRow() {

	let routeContainer = document.getElementById("routeContainer");

	let index = routeContainer.querySelectorAll('.route-row').length;



	let newRow = `
         
                 <div class="route-row row mb-2">
         
                     <div class="col-md-3">
         
                         <input type="text" class="form-control" name="routes[${index}].station" placeholder="Station Name" required>
         
                     </div>
         
                     <div class="col-md-3">
         
                         <input type="time" class="form-control" name="routes[${index}].arrivalTime" required>
         
                     </div>
         
                     <div class="col-md-3">
         
                         <input type="time" class="form-control" name="routes[${index}].departureTime" required>
         
                     </div>
         
                     <div class="col-md-3">
         
                         <button type="button" class="btn btn-sm btn-danger" onclick="removeRow(this)">✖</button>
         
                     </div>
         
                 </div>`;

	routeContainer.insertAdjacentHTML("beforeend", newRow);

}



function addSeatMappingRow() {

	let seatContainer = document.getElementById("seatContainer");

	let index = seatContainer.querySelectorAll('.seat-row').length;



	let newRow = `
         
                 <div class="seat-row row mb-2">
         
                     <div class="col-md-3">
         
                         <input type="text" class="form-control" name="seatMappings[${index}].station" placeholder="Station Name" required>
         
                     </div>
         
                     <div class="col-md-3">
         
                         <input type="date" class="form-control" name="seatMappings[${index}].journeyDate" required>
         
                     </div>
         
                     <div class="col-md-3">
         
                         <input type="text" class="form-control" name="seatMappings[${index}].seats" placeholder="Seats (comma-separated)" required>
         
                     </div>
         
                     <div class="col-md-3">
         
                         <button type="button" class="btn btn-sm btn-danger" onclick="removeRow(this)">✖</button>
         
                     </div>
         
                 </div>`;

	seatContainer.insertAdjacentHTML("beforeend", newRow);

}



function removeRow(button) {

	button.parentElement.parentElement.remove();

}