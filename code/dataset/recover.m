function [] = recover(tag, pathToImages, pathToAnnotations, varargin)
%RECUPERER Summary of this function goes here
%   http://labelme2.csail.mit.edu/Release3.0/browserTools/php/dataset.php 
%   tag : tag of the object (example: 'car')
%   pathToImages 
%   pathToAnnotations 
%   (nbImages)

    inputs = setDefaultsArguments(tag, pathToImages, pathToAnnotations, varargin);

    initialize();

    % create the list of images that you want 
    [Q, ~] = LMquery(database, 'object.name', tag);
    clear folderlist filelist 

    nbImages = min(length(Q), inputs.nbImages);
    
    for i = 1:nbImages;
          folderlist{i} = Q(i).annotation.folder;
          filelist{i} = Q(i).annotation.filename;
    end

    % Install the selected images
    LMinstall (folderlist, filelist, pathToImages, pathToAnnotations);
end


function [inputs] = setDefaultsArguments(tag, pathToImages, pathToAnnotations, varargin)
% local function : http://in.mathworks.com/help/matlab/matlab_prog/local-functions.html 
% default arguments : http://all3fox.github.io/blog/2014/06/17/matlab-function-default-arguments/ 
% 
    parser = inputParser;

    parser.addRequired('tag', @iscellstr);
    parser.addRequired('pathToImages', @iscellstr);
    parser.addRequired('pathToAnnotations', @iscellstr);

    parser.addOptional('nbImages', Inf, @(x) @isnumeric(x) && (x >= 0));
    parser.addOptional('db', [], @(x) true);

    parser.parse(tag, pathToImages, pathToAnnotations, varargin{:});

    inputs = parser.Results;
end 


function [] = initialize(previousDB) 
    persistent database;

    if not isempty(previousDB) 
        disp('[recover] initialize with the saved database');
        database = previousDB;
        D = previousDB;

    elseif isempty(database) 
        disp('[recover] initialize a new database');

        % 
        HOMEIMAGES = 'http://people.csail.mit.edu/brussell/research/LabelMe/Images';
        HOMEANNOTATIONS = 'http://people.csail.mit.edu/brussell/research/LabelMe/Annotations';

        % build the index (take few minutes)
        database = LMdatabase(HOMEANNOTATIONS); 
        D = database;

    else 
        disp('[recover] already initialized');
    end 

end 
