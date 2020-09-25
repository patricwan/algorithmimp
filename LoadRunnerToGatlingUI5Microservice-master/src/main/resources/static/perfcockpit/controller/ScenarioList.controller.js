sap.ui.define([
	"sap/ui/demo/nav/controller/BaseController",
	"sap/ui/demo/nav/model/formatter"
], function (BaseController,formatter) {
	"use strict";

	return BaseController.extend("sap.ui.demo.nav.controller.ScenarioList", {
		formatter: formatter,
		onInit : function() {
			this.refreshData();
			//this.modelServices();
	    },
	    
	    modelServices: function() {
	        var self = this;
	        this.intervalHandle = setInterval(function() { 
	             self.refreshData();
	             console.log("Refresh Data once ");
	         },  10000);
	    },
		
	    refreshData: function() {
			var datasourceUri = this.getOwnerComponent().getManifestEntry("/sap.app/dataSources/scenariorunning/uri") + "scenariosAll";
			console.log("ScenarioList datasourceUri " + datasourceUri);
			var amModel = new sap.ui.model.json.JSONModel(datasourceUri);
			console.log("set model " + amModel);
			
			this.getView().setModel(amModel, "scenarios");
			
			var oTable = this.byId("scenariosTable");
			oTable.bindElement({ path: "/", model: "scenarios" });
	    },
	    
		onItemPressed : function(oEvent){
			var oItem, oCtx;

			oItem = oEvent.getSource();
			oCtx = oItem.getBindingContext();
			
			console.log("Model:" + this.getView().getModel());
			console.log("Property :" + oEvent.getSource().getBindingContext());
			this.getRouter().navTo("scenario", {
				// The source is the list item that got pressed
				scenarioId: oEvent.getSource().getBindingContext("scenarios").getProperty("scenarioID")
			});
			
		},
		
		showCreateScenarioDialog: function(oEvent) {
			if (!this._oDialog || !this._oTable) {
				this._oDialog = sap.ui.xmlfragment("sap.ui.demo.nav.view.AddScenarioDialog", this);
			}

			this.getView().addDependent(this._oDialog);

			this._oDialog.open();
		},
		
		handleSubmit : function () {
			var sScenarioName = sap.ui.getCore().byId('scenarioNameInput').getValue();
			var sLrFileUploadName = sap.ui.getCore().byId('lrFileUploader').getValue();
			console.log("New Scenario Name " + sScenarioName + " " + sLrFileUploadName);
			
			var oFileUploader = sap.ui.getCore().byId('lrFileUploader');
			if (!oFileUploader.getValue()) {
				MessageToast.show("Choose a file first");
				return;
			}
			
			var file = $("input[name='myFileUpload']")[0].files[0];
			console.log("Selected Files " + file);
			
		    var formData = new FormData();
		    formData.append("file", file);

		    var xhr = new XMLHttpRequest();
		    xhr.open("POST", "/uploadFile");
		    xhr.onload = function() {
		        console.log(xhr.responseText);
		        var response = JSON.parse(xhr.responseText);
		        if(xhr.status == 200) {
		           console.log("Upload Done");
		        } else {
		           console.log("Upload error");
		        }
		    }
		    xhr.send(formData);
			
			var datasourceUrl = this.getOwnerComponent().getManifestEntry("/sap.app/dataSources/scenariorunning/uri") + "addScenario";
			var data = { scenarioName: sScenarioName, loadrunnerName: sLrFileUploadName}
			
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
			if (this._oDialog) {
				this._oDialog.destroy();
			}
			this.refreshData();
		},
		
		onExit : function () {
			if (this._oDialog) {
				this._oDialog.destroy();
			}
			if (this.intervalHandle)  {
				  console.log("Will clear the interval ");
			      clearInterval(this.intervalHandle) ;
			}
		},
		
		handleClose: function(oEvent) {
			if (this._oDialog) {
				this._oDialog.destroy();
			}
		}
	});

});
