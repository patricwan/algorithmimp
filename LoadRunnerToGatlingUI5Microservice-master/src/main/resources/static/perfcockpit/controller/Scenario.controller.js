sap.ui.define([ 'sap/m/MessageToast',
	            'sap/ui/demo/nav/controller/BaseController',
	            'sap/ui/demo/nav/model/formatter',
	            'sap/ui/model/json/JSONModel'], function(
	            MessageToast, BaseController, formatter) {
	"use strict";

	return BaseController.extend("sap.ui.demo.nav.controller.Scenario", {
		formatter: formatter,
		onInit : function() {
			
			this.getRouter().getRoute("scenario").attachPatternMatched(
					this._onScenarioMatched, this);
			
			//sap.ui.getCore().byId('convertToGatlingBtn').setTooltip('Convert To Gatling');
			//sap.ui.getCore().byId('startPerfRunBtn').setTooltip('Start Perf Run');
			//this.setModel(oViewModel, "scenarioView");
			//console.log("onInit model " + this.getView().getModel());
			//var sScenarioId = this.byId('scenarioId').getText();
			//console.log("ScenarioId from onInit " + sScenarioId);
			//this.modelServices(sScenarioId);
		}, 
		
	    modelServices: function(sScenarioId) {
	        var self = this;
	        this.intervalHandle = setInterval(function() { 
	             self.refreshData(sScenarioId.trim());
	             console.log("Refresh Data Scenario " + sScenarioId);
	         },  10000);
	    },
	    
		/**
		 * Navigates back to the worklist
		 * @function
		 */
		onNavBack: function () {
			this.getRouter().navTo("scenarioList");
		},
		
		handleClose: function () {
			if (this._oDialog) {
				this._oDialog.destroy();
			}
		}, 
		
		handleConvertToGatling: function () {
			var sScenarioId = this.byId('scenarioId').getText();
			var sGatlingName = this.byId('loadrunnerName').getText() + "gatling";
			console.log("Scenario Id " + sScenarioId + " " + sGatlingName);
			
			//Start the async convert process.
			var asyncConvertUrl=this.getOwnerComponent().getManifestEntry("/sap.app/dataSources/scenariorunning/uri") + "convertToGatling?zipFileName=" + this.byId('loadrunnerName').getText();
			$.ajax({
		        url: asyncConvertUrl,
		    }).then(function(data) {
		    	console.log("Start conversion to Gatling Kicked Off.");
		    });
			
	        var msg = 'Start conversion to Gatling kicked off.\r\n';
			MessageToast.show(msg);
			
			//updateScenarioGatling
			var datasourceUrl=this.getOwnerComponent().getManifestEntry("/sap.app/dataSources/scenariorunning/uri") + "scenario/" + sScenarioId;
			var data = { scenarioID: sScenarioId, gatlingName: sGatlingName};
			
			//Send the data using post
	        $.ajax({
	            url: datasourceUrl,
	            type: 'put',
	            data: JSON.stringify(data),
	            contentType: "application/json; charset=utf-8",
	            dataType: "json",
	            async: false,
	            success: function(result){
	                console.log("Update success");
	           }
	        });
			
	        //var msg = 'Converted to Gatling Done off asynchronously.\r\n';
			//MessageToast.show(msg);
		},
		
		handleStartPerfRun: function() {
			//updateScenarioGatling
			/*var datasourceUrl=this.getOwnerComponent().getManifestEntry("/sap.app/dataSources/scenariorunning/uri") + "testAsync";
			$.ajax({
		        url: datasourceUrl,
		    }).then(function(data) {
		    	console.log("Test Async Started");
		    }); */
			if (!this._oDialog || !this._oTable) {
				this._oDialog = sap.ui.xmlfragment("sap.ui.demo.nav.view.AddPerfRunDialog", this);
			}
			this.getView().addDependent(this._oDialog);
			this._oDialog.open();
		}, 
		
		handleSubmit : function () {
			var sScenarioId = this.byId('scenarioId').getText();
			console.log("ScenarioId" + sScenarioId);
			
			var sBuildVersion = sap.ui.getCore().byId('idBuildVersion').getValue();
			var sMaxUsers = sap.ui.getCore().byId('idMaxUsers').getValue();
			var sdDurationMins = sap.ui.getCore().byId('idDurationMins').getValue();
			
			var datasourceUrl = this.getOwnerComponent().getManifestEntry("/sap.app/dataSources/scenariorunning/uri") + "addPerfRun";
			var dataScenario = {scenarioID: sScenarioId};
			var data = { buildVersion: sBuildVersion, maxUsers: sMaxUsers, duration: sdDurationMins, scenario: dataScenario, status: "Running"};
			
	        //Send the data using post
	        $.ajax({
	            url: datasourceUrl,
	            type: 'POST',
	            data: JSON.stringify(data),
	            contentType: "application/json; charset=utf-8",
	            dataType: "json",
	            async: false,
	            success: function(result){
	                console.log("Post success");
	            }
	        });
			
	        //Also kick off the Jenkins job remotely  startJenkinsJob
	        var jenkinsJobUrl = this.getOwnerComponent().getManifestEntry("/sap.app/dataSources/scenariorunning/uri") + "startJenkinsJob";
			$.ajax({
		        url: jenkinsJobUrl,
		    }).then(function(data) {
		    	console.log("Start Jenkins Job already.");
		    });	
			var msg = 'Start Jenkins Job remotely.\r\n';
			MessageToast.show(msg);
			
	        //Refresh the Data
	        this.refreshData(sScenarioId);
	        
			if (this._oDialog) {
				this._oDialog.destroy();
			}
		}, 
		
		_onScenarioMatched: function (oEvent) {
			var sScenarioId = oEvent.getParameter("arguments").scenarioId;		
			this.refreshData(sScenarioId);
			
		},
		onExit : function () {
			if (this.intervalHandle)  {
				  console.log("Will clear the interval ");
			      clearInterval(this.intervalHandle) ;
			}
		},
		
		refreshData: function(sScenarioId) {
			var datasourceUri=this.getOwnerComponent().getManifestEntry("/sap.app/dataSources/scenariorunning/uri") + "scenario?scenarioId=" + sScenarioId.trim();
			console.log("refresh data from " + datasourceUri);
			var amModel = new sap.ui.model.json.JSONModel(datasourceUri);
			this.getView().setModel(amModel, "scenario");
			
			console.log("_onPostMatched oDataModel " + amModel);
			this.byId("objectHeader").bindElement({
				path: "/",
				model: "scenario",
				events: {
				}
			});
			
			var perfrunDsUri= this.getOwnerComponent().getManifestEntry("/sap.app/dataSources/scenariorunning/uri") + "perfRunsByScenarioID?scenarioID=" + sScenarioId.trim();
			var bModel = new sap.ui.model.json.JSONModel(perfrunDsUri);
			this.getView().setModel(bModel, "perfruns");
			
			var oTable = this.byId("perfrunsTable");
			oTable.bindElement({ path: "/", model: "perfruns" });
		}
	});
});
