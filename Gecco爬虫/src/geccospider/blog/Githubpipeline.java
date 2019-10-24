package geccospider.blog;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;

@PipelineName("GithubPipeline")
public class Githubpipeline implements Pipeline<Blog>{

	@Override
	public void process(Blog bean) {
		// TODO Auto-generated method stub
		System.out.println();
	}

}
