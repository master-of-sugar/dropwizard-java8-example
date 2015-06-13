package com.github.master_of_sugar;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.java8.auth.AuthFactory;
import io.dropwizard.java8.auth.oauth.OAuthFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.text.SimpleDateFormat;

import com.github.master_of_sugar.resource.ExampleResource;

public class ExampleApplication extends Application<ExampleConfiguration>{
	public static void main(String[] args)throws Exception{
		new ExampleApplication().run(args);
	}

	@Override
	public String getName() {
		return "example";
	}

	@Override
	public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
		
		//for Java8 bundle.
		bootstrap.addBundle(new Java8Bundle());

		//for View bundle.
		bootstrap.addBundle(new ViewBundle<>());

		//for Static content
		bootstrap.addBundle(new AssetsBundle("/assets/","/","index.html","/"));
	}

	@Override
	public void run(ExampleConfiguration configuration, Environment environment) throws Exception {
		//date format
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		environment.getObjectMapper().setDateFormat(sdf);
		
		// url pattern
		// ルートではhtmlとかを返したいのでなんとなく
		environment.jersey().setUrlPattern("/api/*");
		
		// add resource
		environment.jersey().register(new ExampleResource());
		
		// add security
		environment.jersey().register(AuthFactory.binder(new OAuthFactory<>(new ExampleAuthenticator(), "realm", String.class)));
		
		// add ExceptionMapper
		environment.jersey().register(new ExampleExceptionMapper());
	}
}
