app.service('appConstants', function(){
	this.operations = {
	NEXT_EDIT : "next_edit",
	NEXT_CREATE : "next_create",
    EDIT : "edit",
    CREATE : "create",
    SUB_EDIT: "sub_edit",
    SUB_CREATE: "sub_create",
    FILTER: "filter"
  };

	this.types = {
		TEXT: "TEXT",
		DATE: "DATE",
		NUMBER: "NUMBER",
		BOOLEAN: "BOOLEAN"
	};
});
