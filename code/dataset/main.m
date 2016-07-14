%
%
%

clear all 

% include sub-folder automatically in script 
addpath(genpath(pwd));

recover('car', './images', './annotations', 10)


save('variables.save');
%load('variables.save');