package org.osmsurround.ra;

import javax.validation.Valid;


import org.osmsurround.ra.analyzer.AnalyzeRelationService;
import org.osmsurround.ra.analyzer.AnalyzerService;
import org.osmsurround.ra.stats.RelationStatistics;
import org.osmsurround.ra.stats.StatisticsService;
import org.osmtools.ra.RelationGoneException;
import org.osmtools.ra.context.AnalyzerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.osmsurround.ra.report.Report;

@Controller
@RequestMapping("/gapAnalyser")
public class GapAnalyserController {

	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private AnalyzeRelationService analyzeRelationService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Report get(@Valid AnalyzeRelationModel analyzeRelationModel, Errors errors) {
		try {
				if (analyzeRelationModel.getRelationId() != null) {
					return analyzeRelationService.analyzeRelation(analyzeRelationModel);
				}
		}
		catch (RelationGoneException e) {
		}
		return null;
	}
}
