function [dataset] = createDatasetIndex(folders)

	HOMEANNOTATIONS = 'http://labelme.csail.mit.edu/Annotations';
	HOMEIMAGES = 'http://labelme.csail.mit.edu/Images';
	
	dataset = LMdatabase(HOMEANNOTATIONS, folders);

end

