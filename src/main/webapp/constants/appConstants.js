app.service('appConstants', function(){
	this.operations = {
    EDIT : "edit",
    CREATE : "create",
    SUB_EDIT: "sub_edit",
    SUB_CREATE: "sub_create"
  };

	this.types = {
		TEXT: "text",
		DATE: "date",
		NUMBER: "number"
	};
});
