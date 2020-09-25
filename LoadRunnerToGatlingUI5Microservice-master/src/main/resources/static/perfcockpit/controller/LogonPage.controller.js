sap.ui.define([
	"sap/ui/demo/nav/controller/BaseController"
], function (BaseController) {
	"use strict";

	return BaseController.extend("sap.ui.demo.nav.controller.LogonPage", {

		onInit: function () {
			var oRouter = this.getRouter();
			
			

		},
		onPressSubmit: function() {
			 this.getRouter().navTo("scenarioList");
		}
	});

});
