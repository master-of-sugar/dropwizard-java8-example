package com.github.master_of_sugar;

import com.codahale.metrics.health.HealthCheck;

public class ExampleHealthCheck extends HealthCheck{
	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}
}
