sap.ui.define([
	   
], function () {
	"use strict";

	return {
		/**
		 * Rounds the number unit value to 2 digits
		 *
		 * @public
		 * @param {string} sValue the number string to be rounded
		 * @returns {string} sValue with 2 digits rounded
		 */
		numberUnit: function (sValue) {
			if (!sValue) {
				return "";
			}

			return parseFloat(sValue).toFixed(2);
		},

		/**
		 * Defines a value state based on the price
		 * 
		 * @public
		 * @param {number} iPrice the price of a post
		 * @returns {string} sValue the state for the price
		 */
		priceState: function (iPrice) {
			if (iPrice < 50) {
				return "Success";
			} else if (iPrice >= 50 && iPrice < 250 ) {
				return "None";
			} else if (iPrice >= 250 && iPrice < 2000 ) {
				return "Warning";
			} else {
				return "Error";
			}
		},
		gatlingStatusColor: function (gatling) {
			if (!gatling) {
				return 3;
			} else {
				return 8;
			}
		}, 
		gatlingNameText: function (gatling) {
			if (!gatling) {
				return "Not Available";
			} else {
				return gatling;
			}
		}, 
		perfRunStatusText: function (status) {
			console.log("Got status " + status);
			if (!status) {
				return "Finished";
			} else {
				return status;
			}
		}, 
		perfRunStatusColor: function (status) {
			if (!status) {
				return 3;
			} else {
				return 8;
			}
		}, 
		gatlingStatus: function (gatling) {
			if (!gatling) {
				return "./images/NotDone.png";
			} else {
				return "./images/Done.png";
			}
		},
		loadrunnerStatus: function (loadrunnername) {
			if (!loadrunnername) {
				return "./images/NotDone.png";
			} else {
				return "./images/Done.png";
			}
			
		}
 };

});
