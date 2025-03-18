module.exports = {
  // The default branch to create PRs for
  baseBranch: 'main',  

  // Automatically merge pull requests if all checks pass
  "automerge": true,

  // Add default settings for all dependencies
  "extends": [
    "config:base"  // Start with default config, can be customized further
  ],

  // Customize update frequency for different package managers
  "packageRules": [
    {
      "managers": ["npm", "yarn", "maven"],
      "updateTypes": ["minor", "patch"],  // Allow minor and patch updates only
    },
    {
      "managers": ["dockerfile"],
      "enabled": true,  // Enable Dockerfile updates
    }
  ],

  // Set up dependency dashboard for tracking updates
  "dependencyDashboard": true,

  // Ignore specific dependencies from being updated
  "ignoreDeps": ["some-dependency-to-ignore"],
};
