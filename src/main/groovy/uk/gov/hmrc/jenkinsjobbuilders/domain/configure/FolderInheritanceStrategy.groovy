/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

class FolderInheritanceStrategy implements Configure {

  private final InheritanceStrategy inheritanceStrategy

  private FolderInheritanceStrategy(final InheritanceStrategy inheritanceStrategy) {
    this.inheritanceStrategy = inheritanceStrategy
  }

  @Override
  Closure toDsl() {
    return { folder ->
      /**
       * The inheritanceStrategy node _must_ be added as the 0th element, otherwise Jenkins doesn't recognise it when it
       * parses the XML.
       */
      final Node matrix = folder / 'properties' / 'com.cloudbees.hudson.plugins.folder.properties.AuthorizationMatrixProperty'
      final NodeList matrixChildren = matrix.value()
      matrixChildren.add(0, new Node(null, "inheritanceStrategy", ['class': inheritanceStrategy.className]))
    }
  }

  static FolderInheritanceStrategy folderInheritanceStrategy(final InheritanceStrategy inheritanceStrategy) {
      return new FolderInheritanceStrategy(inheritanceStrategy)
  }
}
