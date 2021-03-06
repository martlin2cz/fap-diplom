function [y] = MyFuzzySharpener(c, neighs)
	avgNeigh = neighs/8;
	
	y = MyBrokenFun(c, avgNeigh, 1.1);

end

function [z] = MyBrokenFun(x, y, EPSILON)

	invert = 0;
	if (y > x)
		invert = +1;
	elseif (y < x)
		invert = -1; 
	else
		invert = 0;
	end


	zBase = EPSILON .* y;

	if (invert == +1)
		zRaw = zBase .- EPSILON .+ 1;
	elseif (invert == -1)
		zRaw = zBase;
	end

	if (invert == 0)
		z = y;
	else
		z = max(0, min(1, zRaw));
	end

%	z = 1 - zl;
end

%	nc = c - 0.5;
%	nan = avgNeigh - 0.5;


%	%% FUNGUJE, ALE MOC SLOŽITÉ
%	COEF = 0;
%	nyr = 0.5 .*  ((2*nc + 1) .*  (1 .+ (sqrtr((nc .- nan), COEF))));
%	nyl = -0.5 .* ((-2*nc + 1) .* (1 .+ (sqrtr(((-nc) .- (-nan)), COEF)))) + 1;

%	ny = max(nyr, nyl);
%	y = max(0, min(1, ny));



	%% tak taky noop (ale pro COEF == 0 zřejmě konverguje k pseudo labyrintu)
%	COEF = 0;
%	nyr = 0.5 .*  ((-2*nc + 1) .*  (1 .+ (sqrtr((-nc .- nan), COEF))));
%	nyl = -0.5 .* ((2*nc + 1)  .* (1 .+ (sqrtr(((nc) .- (-nan)), COEF)))) + 1;

%	ny = 1 - min(nyr, nyl);
%	y = ny + 0.0;


	%% nop
%	COEF = 0.01;
%	y = max(0, min(1, ((2 .* nc .+ 0.5) .+ (COEF .* nan))));

	%% FUNGUJE, ALE MOC SLOŽITÉ
%	COEF = 0;
%	nyr = 0.5 .*  ((2*nc + 1) .*  (1 .+ (sqrtr((nc .- nan), COEF))));
%	nyl = -0.5 .* ((-2*nc + 1) .* (1 .+ (sqrtr(((-nc) .- (-nan)), COEF)))) + 1;

%	ny = min(nyr, nyl);
%	y = ny + 0.0;
	
 	%% XXXXX

	%ny = max(0, min(1, (nc .+ 0.5) .* (0.5 .+ sqrtr(nc .- nan))));
	%ny = -0.4.*exp(((-nc) .* (-nan)) .- max(nc, nan));
	
%		y = 1 - (abs(c - avgNeigh))^0.55;

%			if c > 0.5   % živá buňka
%        if neighs < 4 
%          y = 0;    
%        else
%          y = 1;    
%        end
%      else      % mrtvá buňka
%        if neighs > 4
%          y = 1;
%        else
%          y = 0;
%        end
%      end
