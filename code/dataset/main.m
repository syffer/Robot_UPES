%
%
%

%clear all 

% include sub-folder automatically in script 
%addpath(genpath(pwd));

folders = { '05june05_static_street_boston', 
			'05june05_static_street_porter',
			'10feb04_static_cars_highland', 
			'10feb04_static_cars_techsquare_lot', 
			'april21_static_outdoor_davis', 
			'april21_static_outdoor_kendall', 
			'barcelona_static_street', 
			'database_static_street', 
			'madrid_static_street', 
			'nov6_static_outdoor' };

downloadedImages = './images';
downloadedAnnotations = './annotations';


%dataset = createDatasetIndex(folders);

recover(dataset, 'car', downloadedImages, downloadedAnnotations, 10);
recover(dataset, 'person', downloadedImages, downloadedAnnotations, 10);
recover(dataset, 'rock', downloadedImages, downloadedAnnotations, 10);
recover(dataset, 'tree', downloadedImages, downloadedAnnotations, 10);
recover(dataset, 'wall', downloadedImages, downloadedAnnotations, 10);

% car, person, rock, tree, wall 
