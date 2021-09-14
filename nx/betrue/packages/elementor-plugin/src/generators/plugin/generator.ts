import {
  addProjectConfiguration,
  formatFiles,
  generateFiles,
  getWorkspaceLayout,
  names,
  logger,
  offsetFromRoot,
  Tree, convertNxGenerator,
} from '@nrwl/devkit';
import * as path from 'path';
import {ElementorPluginGeneratorSchema} from './schema';
import elementorInitGenerator from "../init/init"
import {runTasksInSerial} from "@nrwl/workspace/src/utilities/run-tasks-in-serial";
import {ProjectType} from "@nrwl/tao/src/shared/workspace";
import {Linter} from "@nrwl/linter";
import {assertValidStyle, SupportedStyles} from "@nrwl/react";
import {addProject} from "@nrwl/react/src/generators/application/lib/add-project";
import {NormalizedSchema} from "@nrwl/react/src/generators/application/schema";

interface ElementorNormalizedSchema extends ElementorPluginGeneratorSchema {
  projectName: string;
  projectRoot: string;
  projectDirectory: string;
  styledModule: null | SupportedStyles;
  parsedTags: string[];
}

function normalizeOptions(
  host: Tree,
  options: ElementorPluginGeneratorSchema
): ElementorNormalizedSchema {
  const name = names(options.name).fileName;
  const projectDirectory = options.directory
    ? `${names(options.directory).fileName}/${name}`
    : name;
  const projectName = projectDirectory.replace(new RegExp('/', 'g'), '-');
  const projectRoot = `${getWorkspaceLayout(host).appsDir}/${projectDirectory}`;
  const appProjectRoot = projectRoot
  const parsedTags = options.tags
    ? options.tags.split(',').map((s) => s.trim())
    : [];

  const styledModule = /^(css|scss|less|styl|none)$/.test(options.style)
    ? null
    : options.style;

  assertValidStyle(options.style);
  options.strict = options.strict ?? true;
  options.classComponent = options.classComponent ?? false;
  options.unitTestRunner = options.unitTestRunner ?? 'jest';
  options.e2eTestRunner = options.e2eTestRunner ?? 'cypress';

  logger.info(projectName)
  return {
    ...options,
    appProjectRoot,
    projectName,
    projectRoot,
    projectDirectory,
    parsedTags,
    styledModule,
    name: name
  };
}

function addFiles(host: Tree, options: ElementorNormalizedSchema) {
  const templateOptions = {
    ...options,
    ...names(options.name),
    offsetFromRoot: offsetFromRoot(options.projectRoot),
    template: ''
  };
  console.log('generateFiles start')
  generateFiles(
    host,
    path.join(__dirname, 'files'),
    options.projectRoot,
    templateOptions,
  );
  console.log('generateFiles end')

}

export async function pluginGenerator(
  host: Tree,
  options: ElementorPluginGeneratorSchema
) {
  const normalizedOptions = normalizeOptions(host, options);

  addProject(host, normalizedOptions)
  /** addProjectConfiguration(host, normalizedOptions.projectName, {
    root: normalizedOptions.projectRoot,
    projectType: 'application',
    sourceRoot: `${normalizedOptions.projectRoot}/`,
    targets: {
      build: {
        executor: '@betrue/elementor-plugin:build',
      },
    },
    tags: normalizedOptions.parsedTags,
  });*/

  addFiles(host, normalizedOptions);
  const elementorTask = await elementorInitGenerator(host, {
    ...options,
    skipFormat: true,
  });
  await formatFiles(host);
  return runTasksInSerial(
    elementorTask
  );
}

export const pluginSchematic = convertNxGenerator(pluginGenerator);
