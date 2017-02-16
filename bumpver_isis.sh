VERSION=$1

if [ ! "$VERSION" ]; then
    echo "usage: $(basename $0) [version]"
    exit 1
fi

# edit parent pom.xml
echo "editing parent pom.xml"
echo mvn versions:update-parent "-DparentVersion=[${VERSION}]"
mvn versions:update-parent "-DparentVersion=[${VERSION}]"

echo "Committing changes"
git commit -am "bumping incode-parent (isis) version to $VERSION"

