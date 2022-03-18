package test

import (
	"github.com/gruntwork-io/terratest/modules/helm"
	http_helper "github.com/gruntwork-io/terratest/modules/http-helper"
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestPodDeploysContainerImageHelmTemplateEngine(t *testing.T) {
	// Path to the helm chart we will test
	helmChartPath := "../spring-boot-demo"

	options := &helm.Options{
		ValuesFiles: []string{"../local-values.yaml"},
		ExtraArgs:   map[string][]string{"upgrade": {"-i"}},
	}

	helm.Upgrade(t, options, helmChartPath, "spring-boot-demo-instance")

	status, _ := http_helper.HttpGet(t, "http://spring-boot-demo.local/hero", nil)

	assert.Equal(t, 200, status)
}
