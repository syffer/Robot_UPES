function [] = recover(dataset, tag, pathToImages, pathToAnnotations, varargin)
%RECUPERER Summary of this function goes here
%   http://labelme2.csail.mit.edu/Release3.0/browserTools/php/dataset.php 
%   tag : tag of the object (example: 'car')
%   pathToImages 
%   pathToAnnotations 
%   (nbImages)

    inputs = setDefaultsArguments(dataset, tag, pathToImages, pathToAnnotations, varargin{:}); 

    % create the list of images that you want 
    [Q, ~] = LMquery(inputs.dataset, 'object.name', tag);

    folders = {};
    files = {};

    nbImages = min(length(Q), inputs.nbImages);
    
    for i = 1:nbImages;
          folders{i} = Q(i).annotation.folder;
          files{i} = Q(i).annotation.filename;
    end
    
    % Install the selected images
    LMinstall(folders, files, pathToImages, pathToAnnotations, 'flat');
end


function [inputs] = setDefaultsArguments(dataset, tag, pathToImages, pathToAnnotations, varargin)
% local function : http://in.mathworks.com/help/matlab/matlab_prog/local-functions.html 
% default arguments : http://all3fox.github.io/blog/2014/06/17/matlab-function-default-arguments/ 
% 
    parser = inputParser;

    checknum = @(x) isnumeric(x) && isscalar(x) && (x >= 0);

    parser.addRequired('dataset', @(x) true);
    parser.addRequired('tag', @ischar);
    parser.addRequired('pathToImages', @ischar);
    parser.addRequired('pathToAnnotations', @ischar);
    parser.addOptional('nbImages', Inf, checknum);
    
    parser.parse(dataset, tag, pathToImages, pathToAnnotations, varargin{:});

    inputs = parser.Results;
end 
